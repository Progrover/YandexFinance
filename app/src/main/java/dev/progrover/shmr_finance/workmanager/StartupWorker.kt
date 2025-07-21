package dev.progrover.shmr_finance.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.articles.api.domain.interactor.ArticlesInteractor
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.utils.dateToServerRequest
import dev.progrover.core.base.utils.formatToIsoUtc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class StartupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val localTransactionProvider: LocalTransactionProvider,
    private val localArticleProvider: LocalArticleProvider,
    private val localAccountProvider: LocalAccountProvider,
    private val accountInteractor: AccountInteractor,
    private val transactionsApi: TransactionsApi,
    private val articlesInteractor: ArticlesInteractor,
    private val prefs: Prefs,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            Timber.d("StartupWorker started")
            getAccount()
            getArticles()
            getTransactions()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): StartupWorker
    }

    private suspend fun getTransactions() = withContext(Dispatchers.IO) {
        try {
            val accountId = localAccountProvider.getAllAccounts().first().id ?: -1
            val transactions = transactionsApi.getTransactions(
                accountId = accountId,
                startDate = leftBorder().dateToServerRequest(),
                endDate = rightBorder().dateToServerRequest(),
            )
            if (transactions.isSuccessful && transactions.body() != null) {
                transactions.body()!!.forEach { transaction ->
                    localTransactionProvider.createTransaction(transaction, true)
                }
            }
            updateDate()
        } catch (e: Exception) {
            Timber.e("getTransactions Worker error: ${e.message}")
        }
    }

    private fun updateDate() {
        prefs.putString("LAST_UPDATE_TIME", Instant.now().toEpochMilli().formatToIsoUtc())
    }

    private suspend fun getArticles() = withContext(Dispatchers.IO) {
        try {
            val incomes = articlesInteractor.getCategoriesByType(true)
            val expenditures = articlesInteractor.getCategoriesByType(false)

            if (incomes.value != null) incomes.value!!.forEach { article ->
                localArticleProvider.createCategory(
                    article
                )
            }
            if (expenditures.value != null) expenditures.value!!.forEach { article ->
                localArticleProvider.createCategory(
                    article
                )
            }
        } catch (e: Exception) {
            Timber.e("GetArticles Worker error: ${e.message}")
        }
    }

    private suspend fun getAccount() = withContext(Dispatchers.IO) {
        try {
            val response = accountInteractor.getAccounts()
            if (response.value != null) {
                val account = response.value!!.first()
                localAccountProvider.updateAccount(
                    account,
                    true
                )
            }
        } catch (e: Exception) {
            Timber.e("UpdateAccount Worker error: ${e.message}")
        }
    }

    private fun leftBorder() =
        ZonedDateTime.now(ZoneOffset.UTC).minusYears(100).toInstant().toEpochMilli()

    private fun rightBorder() =
        ZonedDateTime.now(ZoneOffset.UTC).plusYears(100).toInstant().toEpochMilli()
}
package dev.progrover.shmr_finance.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.articles.api.domain.interactor.ArticlesInteractor
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.model.Transaction
import dev.progrover.core.base.model.request.CreateTransactionRequest
import dev.progrover.core.base.model.request.UpdateTransactionRequest
import dev.progrover.core.base.utils.dateToServerRequest
import dev.progrover.core.base.utils.formatToIsoUtc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val localTransactionProvider: LocalTransactionProvider,
    private val localArticleProvider: LocalArticleProvider,
    private val localAccountProvider: LocalAccountProvider,
    private val accountInteractor: AccountInteractor,
    private val transactionsApi: TransactionsApi,
    private val articlesInteractor: ArticlesInteractor,
    private val accountPropertiesProvider: AccountPropertiesProvider,
    private val prefs: Prefs,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Timber.d("SyncWorker started")
            updateAccount()
            updateArticles()
            updateTransactions()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): SyncWorker
    }

    private suspend fun updateAccount() = withContext(Dispatchers.IO) {
        try {
            val localAccount = localAccountProvider.getAllAccounts().first()
            accountInteractor.updateAccount(localAccount)
            Timber.d("SyncWorker: account update completed")
        } catch (e: Exception) {
            Timber.e("UpdateAccount Worker error: ${e.message}")
        }
    }

    private suspend fun updateTransactions() = withContext(Dispatchers.IO) {
        try {
            accountPropertiesProvider.getId(this) { result ->
                result.fold(
                    onSuccess = { id ->
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val localTransactions = localTransactionProvider
                                    .getTransactionsByAccountAndPeriod(
                                        id,
                                        leftBorder(),
                                        rightBorder()
                                    )

                                val serverTransactionResponse = transactionsApi.getTransactions(
                                    accountId = id,
                                    startDate = leftBorder().dateToServerRequest(),
                                    endDate = rightBorder().dateToServerRequest(),
                                )
                                val serverTransactions = serverTransactionResponse.body()
                                val unsyncedLocalTransactions =
                                    localTransactions.mapNotNull { if (it.synced == false) it else null }
                                Timber.d("SyncWorker: local to sync: $unsyncedLocalTransactions")
                                val serverToUpdate = mutableListOf<Transaction>()
                                val serverToCreate = mutableListOf<Transaction>()

                                if (serverTransactionResponse.isSuccessful && serverTransactions != null) {
                                    //Заполнение транзакций для обновления и создания на сервере
                                    unsyncedLocalTransactions.forEach { localTransaction ->
                                        serverTransactions.find { it.id == localTransaction.id }
                                            ?.let {
                                                serverToUpdate.add(localTransaction)
                                            } ?: serverToCreate.add(localTransaction)
                                    }

                                    //обновляем данные на сервере
                                    serverToUpdate.forEach { item ->
                                        transactionsApi.updateTransactionById(
                                            item.id,
                                            updateTransactionRequest = UpdateTransactionRequest(
                                                accountId = item.account.id,
                                                categoryId = item.category.id,
                                                amount = item.amount,
                                                transactionDate = item.transactionDate,
                                                comment = item.comment
                                            ),
                                        )
                                    }

                                    serverToCreate.forEach { item ->
                                        transactionsApi.createTransaction(
                                            createTransactionRequest = CreateTransactionRequest(
                                                accountId = item.account.id,
                                                categoryId = item.category.id,
                                                amount = item.amount,
                                                transactionDate = item.transactionDate,
                                                comment = item.comment
                                            ),
                                        )
                                    }
                                    //обновляем локально
                                    val updatedVersion = transactionsApi.getTransactions(
                                        accountId = id,
                                        startDate = leftBorder().dateToServerRequest(),
                                        endDate = rightBorder().dateToServerRequest(),
                                    ).body()
                                    Timber.d("SyncWorker: updated version: $updatedVersion")

                                    if (!updatedVersion.isNullOrEmpty()) {
                                        localTransactionProvider.clearTransactions()
                                        updatedVersion.forEach { transaction ->
                                            localTransactionProvider.createTransaction(
                                                transaction,
                                                true
                                            )
                                        }
                                    }

                                    updateDate()
                                }
                            } catch (e: Exception) {
                                Timber.e("UpdateTransactions Worker error: ${e.message}")
                            }
                        }
                    },
                    onFailure = {

                    }
                )
            }
            Timber.d("SyncWorker: transactions update completed")
        } catch (e: Exception) {
            Timber.e("UpdateTransactions Worker error: ${e.message}")
        }
    }

    private suspend fun updateArticles() = withContext(Dispatchers.IO) {
        try {
            val incomes = articlesInteractor.getCategoriesByType(true)
            val expenditures = articlesInteractor.getCategoriesByType(false)
            val localArticles = localArticleProvider.getAllCategories()

            if (incomes.value != null) incomes.value!!.forEach { article ->
                if (!localArticles.contains(article))
                    localArticleProvider.createCategory(
                        article
                    )
            }
            if (expenditures.value != null) expenditures.value!!.forEach { article ->
                if (!localArticles.contains(article))
                    localArticleProvider.createCategory(
                        article
                    )
            }
            Timber.d("SyncWorker: articles update completed")
        } catch (e: Exception) {
            Timber.e("UpdateArticles Worker error: ${e.message}")
        }
    }

    private fun updateDate() {
        prefs.putString("LAST_UPDATE_TIME", Instant.now().toEpochMilli().formatToIsoUtc())
    }

    private fun leftBorder() =
        ZonedDateTime.now(ZoneOffset.UTC).minusYears(100).toInstant().toEpochMilli()

    private fun rightBorder() =
        ZonedDateTime.now(ZoneOffset.UTC).plusYears(100).toInstant().toEpochMilli()
}
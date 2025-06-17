package dev.progrover.incomes.impl.data.repository

import android.annotation.SuppressLint
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.utils.Variables
import dev.progrover.incomes.impl.data.mapper.IncomesDTOMapper
import dev.progrover.incomes.impl.domain.model.Income
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val transactionsApi: TransactionsApi,
    private val incomesDTOMapper: IncomesDTOMapper,
) : IncomesRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    @SuppressLint("SimpleDateFormat")
    override suspend fun getIncomes(accountId: Int): Result<Pair<String, List<Income>>> =
        executeOnIO {
            try {
                val date = SimpleDateFormat("yyyy-MM-dd").format(Date())

                if (tokenAvaliable) {
                    val response = transactionsApi.getTransactions(
                        accountId = accountId,
                        startDate = date,
                        endDate = date,
                    ).filter { transaction ->
                        transaction.category.isIncome
                    }

                    val currency =
                        if (response.isNotEmpty()) response.first().account.currency else "RUB"

                    Result.success(
                        Pair(
                            currency,
                            incomesDTOMapper.mapTransactionsToIncomes(response)
                        )
                    )
                } else {
                    Result.failure(Exception(Variables.TOKEN_ERROR))
                }
            } catch (e: Exception) {
                Timber.e("GetExpenditures error", e)
                Result.failure(getErrorMessage(e))
            }
        }
}
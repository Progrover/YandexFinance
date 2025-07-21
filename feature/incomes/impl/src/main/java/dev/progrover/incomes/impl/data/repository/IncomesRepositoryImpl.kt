package dev.progrover.incomes.impl.data.repository

import android.annotation.SuppressLint
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.utils.getEndOfToday
import dev.progrover.core.base.utils.getStartOfToday
import dev.progrover.core.base.utils.serverRequestToMillisEndOfDay
import dev.progrover.core.base.utils.serverRequestToMillisStartOfDay
import dev.progrover.core.base.utils.toMillis
import dev.progrover.incomes.api.domain.model.IncomeDetailed
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
    private val localTransactionProvider: LocalTransactionProvider,
) : IncomesRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    @SuppressLint("SimpleDateFormat")
    override suspend fun getIncomes(
        accountId: Int,
    ): ApiResponse<List<Income>> =
        executeOnIO {
            try {
                val startDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
                val endDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

                if (tokenAvaliable) {
                    val response = transactionsApi.getTransactions(
                        accountId = accountId,
                        startDate = startDate,
                        endDate = endDate,
                    )
                    if (response.isSuccessful) {
                        val result = response.body()!!.filter { transaction ->
                            transaction.category.isIncome
                        }.sortedBy { it.transactionDate.toMillis() }
                        ApiResponse(
                            value =
                                incomesDTOMapper.mapTransactionsToIncomes(result)
                        )
                    } else {
                        ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetIncomes error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getIncomesFromLocalStorage(accountId: Int): ApiResponse<List<Income>> =
        executeOnIO {
            try {
                val startDate = getStartOfToday()
                val endDate = getEndOfToday()
                if (tokenAvaliable) {
                    val response = localTransactionProvider.getTransactionsByAccountAndPeriod(
                        accountId = accountId,
                        startDate = startDate,
                        endDate = endDate
                    )

                    val result = response.filter { transaction ->
                        transaction.category.isIncome
                    }.sortedBy { it.transactionDate.toMillis() }

                    ApiResponse(value = incomesDTOMapper.mapTransactionsToIncomes(result))
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetIncomes locally error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getIncomesDetailed(
        accountId: Int,
        start: String,
        end: String
    ): ApiResponse<List<IncomeDetailed>> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = transactionsApi.getTransactions(
                        accountId = accountId,
                        startDate = start,
                        endDate = end,
                    )
                    if (response.isSuccessful) {
                        val result = response.body()!!.filter { transaction ->
                            transaction.category.isIncome
                        }.sortedBy { it.transactionDate.toMillis() }
                        ApiResponse(
                            value =
                                incomesDTOMapper.mapTransactionsToIncomesDetailed(result)
                        )
                    } else {
                        ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetIncomesDetailed locally error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getIncomesDetailedFromLocalStorage(
        accountId: Int,
        start: String,
        end: String
    ): ApiResponse<List<IncomeDetailed>> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = localTransactionProvider.getTransactionsByAccountAndPeriod(
                        accountId = accountId,
                        startDate = start.serverRequestToMillisStartOfDay(),
                        endDate = end.serverRequestToMillisEndOfDay(),
                    )

                    val result = response.filter { transaction ->
                        transaction.category.isIncome
                    }.sortedBy { it.transactionDate.toMillis() }

                    ApiResponse(
                        value = incomesDTOMapper.mapTransactionsToIncomesDetailed(
                            result
                        )
                    )
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetIncomesDetailed error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }
}
package dev.progrover.expenditures.impl.data.repository

import android.annotation.SuppressLint
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.utils.getEndOfToday
import dev.progrover.core.base.utils.getStartOfToday
import dev.progrover.core.base.utils.serverRequestToMillis
import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.impl.domain.model.Expenditure
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class ExpendituresRepositoryImpl @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val transactionsApi: TransactionsApi,
    private val expendituresDTOMapper: ExpendituresDTOMapper,
    private val localTransactionProvider: LocalTransactionProvider,
) : ExpendituresRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    @SuppressLint("SimpleDateFormat")
    override suspend fun getExpenditures(accountId: Int): ApiResponse<List<Expenditure>> =
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
                            !transaction.category.isIncome
                        }
                        ApiResponse(
                            value = expendituresDTOMapper.mapTransactionsToExpenditures(
                                result
                            )
                        )
                    } else {
                        ApiResponse(response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetExpenditures error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getExpendituresFromLocalStorage(accountId: Int): ApiResponse<List<Expenditure>> =
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

                    ApiResponse(value = expendituresDTOMapper.mapTransactionsToExpenditures(response))
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetExpenditures locally error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getExpendituresDetailed(
        accountId: Int,
        start: String,
        end: String
    ): ApiResponse<List<ExpenditureDetailed>> =
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
                            !transaction.category.isIncome
                        }
                        ApiResponse(
                            value =
                                expendituresDTOMapper.mapTransactionsToExpendituresDetailed(result)
                        )
                    } else {
                        ApiResponse(response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetExpenditures error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getExpendituresDetailedFromLocalStorage(
        accountId: Int,
        start: String,
        end: String
    ): ApiResponse<List<ExpenditureDetailed>> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = localTransactionProvider.getTransactionsByAccountAndPeriod(
                        accountId = accountId,
                        startDate = start.serverRequestToMillis(),
                        endDate = end.serverRequestToMillis(),
                    )

                    ApiResponse(
                        value = expendituresDTOMapper.mapTransactionsToExpendituresDetailed(
                            response
                        )
                    )
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetExpendituresDetailed locally error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }
}
package dev.progrover.expenditures.impl.data.repository

import android.annotation.SuppressLint
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.api.domain.model.Expenditure
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
) : ExpendituresRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    @SuppressLint("SimpleDateFormat")
    override suspend fun getExpenditures(accountId: Int, start: String?, end: String?): ApiResponse<Pair<String, List<Expenditure>>> =
        executeOnIO {
            try {
                val startDate = start ?: SimpleDateFormat("yyyy-MM-dd").format(Date())
                val endDate = end ?: SimpleDateFormat("yyyy-MM-dd").format(Date())
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
                        val currency =
                            if (result.isNotEmpty()) result.first().account.currency else "RUB"
                        ApiResponse(
                            value = Pair(
                                currency,
                                expendituresDTOMapper.mapTransactionsToExpenditures(result)
                            )
                        )
                    } else ApiResponse(response.code())
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetExpenditures error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }
}
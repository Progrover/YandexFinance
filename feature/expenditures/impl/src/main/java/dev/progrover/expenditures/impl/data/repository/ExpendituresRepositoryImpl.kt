package dev.progrover.expenditures.impl.data.repository

import android.annotation.SuppressLint
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.utils.Variables
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
) : ExpendituresRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    @SuppressLint("SimpleDateFormat")
    override suspend fun getExpenditures(accountId: Int): Result<Pair<String, List<Expenditure>>> =
        executeOnIO {
            try {
                val date = SimpleDateFormat("yyyy-MM-dd").format(Date())

                if (tokenAvaliable) {
                    val response = transactionsApi.getTransactions(
                        accountId = accountId,
                        startDate = date,
                        endDate = date,
                    ).filter { transaction ->
                        !transaction.category.isIncome
                    }

                    val currency =
                        if (response.isNotEmpty()) response.first().account.currency else "RUB"

                    Result.success(
                        Pair(
                            currency,
                            expendituresDTOMapper.mapTransactionsToExpenditures(response)
                        )
                    )
                } else {
                    Result.failure(Exception(Variables.TOKEN_ERROR))
                }
            } catch (e: Exception) {
                val throwable = when (e) {
                    is java.net.UnknownHostException -> Exception(Variables.INTERNET_ERROR)
                    is java.net.ConnectException -> Exception(Variables.INTERNET_ERROR)
                    else -> Exception(Variables.UNKNOWN_ERROR)
                }

                Timber.e("GetExpenditures error", e)
                Result.failure(throwable)
            }
        }
}
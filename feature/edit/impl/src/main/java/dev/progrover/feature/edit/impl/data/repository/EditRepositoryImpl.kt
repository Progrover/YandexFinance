package dev.progrover.feature.edit.impl.data.repository

import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.Account
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.Transaction
import dev.progrover.core.base.model.request.CreateTransactionRequest
import dev.progrover.core.base.model.request.UpdateTransactionRequest
import dev.progrover.core.base.utils.formatToIsoUtc
import dev.progrover.feature.edit.impl.data.mapper.EditDTOMapper
import dev.progrover.feature.edit.impl.domain.model.EditTransaction
import dev.progrover.feature.edit.impl.domain.repository.EditRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class EditRepositoryImpl @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val transactionsApi: TransactionsApi,
    private val editDTOMapper: EditDTOMapper,
    private val localTransactionProvider: LocalTransactionProvider,
    private val localAccountProvider: LocalAccountProvider,
    private val localArticleProvider: LocalArticleProvider,
) : EditRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    override suspend fun getEditTransaction(
        accountId: Int,
        transactionId: Int
    ): ApiResponse<EditTransaction> =
        try {
            if (tokenAvaliable) {
                val response = transactionsApi.getTransactionById(transactionId)

                if (response.isSuccessful && response.body() != null) {
                    ApiResponse(value = editDTOMapper.mapTransactionToEditTransaction(response.body()!!))
                } else {
                    ApiResponse(code = response.code())
                }
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("GetEditTransaction error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun updateTransactionInfo(transaction: EditTransaction): ApiResponse<Boolean> =
        try {
            if (tokenAvaliable) {
                val response = transactionsApi.updateTransactionById(
                    transactionId = transaction.id!!,
                    updateTransactionRequest = UpdateTransactionRequest(
                        accountId = transaction.accountId,
                        categoryId = transaction.categoryId,
                        amount = transaction.amount,
                        transactionDate = transaction.dateTime.formatToIsoUtc(),
                        comment = transaction.comment,
                    )
                )

                if (response.isSuccessful) {
                    ApiResponse(value = true)
                } else {
                    ApiResponse(code = response.code())
                }
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("UpdateTransactionInfo error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun addTransaction(transaction: EditTransaction): ApiResponse<Boolean> =
        try {
            if (tokenAvaliable) {
                val response = transactionsApi.createTransaction(
                    createTransactionRequest = CreateTransactionRequest(
                        accountId = transaction.accountId,
                        categoryId = transaction.categoryId,
                        amount = transaction.amount,
                        transactionDate = transaction.dateTime.formatToIsoUtc(),
                        comment = transaction.comment,
                    )
                )

                if (response.isSuccessful) {
                    ApiResponse(value = true)
                } else {
                    ApiResponse(code = response.code())
                }
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("CreateTransactionInfo error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun deleteTransaction(transactionId: Int): ApiResponse<Boolean> =
        try {
            if (tokenAvaliable) {
                val response = transactionsApi.deleteTransactionById(transactionId)

                if (response.isSuccessful) {
                    ApiResponse(value = true)
                } else {
                    ApiResponse(code = response.code())
                }
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("DeleteTransaction error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun getEditTransactionFromLocalStorage(
        accountId: Int,
        transactionId: Int
    ): ApiResponse<EditTransaction> =
        try {
            if (tokenAvaliable) {
                val response = localTransactionProvider.getTransactionById(transactionId)

                ApiResponse(value = editDTOMapper.mapTransactionToEditTransaction(response!!))
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("GetEditTransaction locally error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun updateTransactionInfoInLocalStorage(
        transaction: EditTransaction,
        synced: Boolean,
    ): ApiResponse<Boolean> =
        try {
            if (tokenAvaliable) {
                localTransactionProvider.updateTransaction(
                    transaction = Transaction(
                        id = transaction.id!!,
                        account = localAccountProvider.getAccountById(transaction.accountId)
                            .let { detailed ->
                                Account(
                                    id = detailed!!.id,
                                    name = detailed.currency,
                                    balance = detailed.balance,
                                    currency = detailed.currency,
                                )
                            },
                        category = localArticleProvider.getCategoryById(transaction.categoryId)!!,
                        amount = transaction.amount,
                        transactionDate = transaction.dateTime.formatToIsoUtc(),
                        comment = transaction.comment,
                        createdAt = LocalDate.now().toEpochDay().formatToIsoUtc(),
                        updatedAt = LocalDate.now().toEpochDay().formatToIsoUtc()
                    ),
                    synced = synced
                )

                ApiResponse(value = true)
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("UpdateTransactionInfo locally error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun addTransactionToLocalStorage(
        transaction: EditTransaction,
        synced: Boolean,
    ): ApiResponse<Boolean> =
        try {
            if (tokenAvaliable) {
                localTransactionProvider.createTransaction(
                    transaction = Transaction(
                        id = transaction.id!!,
                        account = localAccountProvider.getAccountById(transaction.accountId)
                            .let { detailed ->
                                Account(
                                    id = detailed!!.userId,
                                    name = detailed.currency,
                                    balance = detailed.balance,
                                    currency = detailed.currency,
                                )
                            },
                        category = localArticleProvider.getCategoryById(transaction.categoryId)!!,
                        amount = transaction.amount,
                        transactionDate = transaction.dateTime.formatToIsoUtc(),
                        comment = transaction.comment,
                        createdAt = LocalDate.now().toEpochDay().formatToIsoUtc(),
                        updatedAt = LocalDate.now().toEpochDay().formatToIsoUtc()
                    ),
                    synced = synced
                )

                ApiResponse(value = true)
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("AddTransaction locally error", e)
            ApiResponse(error = getErrorMessage(e))
        }
}
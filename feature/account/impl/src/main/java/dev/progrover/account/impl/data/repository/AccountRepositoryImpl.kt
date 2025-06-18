package dev.progrover.account.impl.data.repository

import dev.progrover.account.impl.data.api.AccountApi
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val accountApi: AccountApi,
) : AccountRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    override suspend fun getAccounts(): ApiResponse<List<AccountDetailed>> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.getAccounts()

                    when (response.isSuccessful) {
                        true -> ApiResponse(value = response.body())
                        false -> ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetAccounts error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun getAccountById(accountId: Int): ApiResponse<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.getAccountById(accountId)

                    when (response.isSuccessful) {
                        true -> ApiResponse(value = response.body())
                        false -> ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetAccountById error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun createAccount(
        name: String,
        balance: String,
        currency: String
    ): ApiResponse<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.createNewAccount(
                        name,
                        balance,
                        currency,
                    )

                    when (response.isSuccessful) {
                        true -> ApiResponse(value = response.body())
                        false -> ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("CreateAccount error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun updateAccountById(account: AccountDetailed): ApiResponse<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.updateAccountById(
                        id = account.id,
                        name = account.name,
                        balance = account.balance,
                        currency = account.currency,
                    )

                    when (response.isSuccessful) {
                        true -> ApiResponse(value = response.body())
                        false -> ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("UpdateAccountById error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }

    override suspend fun deleteAccountById(accountId: Int): ApiResponse<Boolean> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.deleteAccountById(accountId)

                    when (response.isSuccessful) {
                        true -> ApiResponse(value = true)
                        false -> ApiResponse(code = response.code())
                    }
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("DeleteAccountById error", e)
                ApiResponse(error = getErrorMessage(e))
            }
        }
}


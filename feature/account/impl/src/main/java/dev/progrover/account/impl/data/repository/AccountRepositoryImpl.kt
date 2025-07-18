package dev.progrover.account.impl.data.repository

import dev.progrover.account.impl.data.api.AccountApi
import dev.progrover.account.impl.data.model.ManageAccountRequest
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.ServerError
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
    private val localAccountProvider: LocalAccountProvider,
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

    override suspend fun getAccountsFromLocalStorage(): ApiResponse<List<AccountDetailed>> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = localAccountProvider.getAllAccounts()

                    ApiResponse(value = response)
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetAccounts locally error", e)
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

    override suspend fun getAccountByIdFromLocalStorage(accountId: Int): ApiResponse<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = localAccountProvider.getAccountById(accountId)

                    ApiResponse(value = response)
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("GetAccountById locally error", e)
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
                        accountRequest = ManageAccountRequest(
                            name = name,
                            currency = currency,
                            balance = balance,
                        )
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
                        accountRequest = ManageAccountRequest(
                            name = account.name,
                            currency = account.currency,
                            balance = account.balance,
                        )
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

    override suspend fun updateAccountByIdFromLocalStorage(
        account: AccountDetailed,
        synced: Boolean
    ): ApiResponse<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = localAccountProvider.updateAccount(
                        account = account,
                        synced = synced
                    )

                    if (response) ApiResponse(value = account)
                    else ApiResponse(error = ServerError.UnknownError)
                } else {
                    ApiResponse()
                }
            } catch (e: Exception) {
                Timber.e("UpdateAccountById locally error", e)
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

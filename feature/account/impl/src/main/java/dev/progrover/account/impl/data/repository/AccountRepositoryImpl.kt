package dev.progrover.account.impl.data.repository

import dev.progrover.account.impl.data.api.AccountApi
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.utils.Variables
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
    override suspend fun getAccounts(): Result<List<AccountDetailed>> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.getAccounts()

                    Result.success(response)
                } else {
                    Result.failure(Exception(Variables.TOKEN_ERROR))
                }
            } catch (e: Exception) {
                Timber.e("GetAccounts error", e)
                Result.failure(getErrorMessage(e))
            }
        }

    override suspend fun getAccountById(accountId: Int): Result<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.getAccountById(accountId)

                    Result.success(response)
                } else {
                    Result.failure(Exception(Variables.TOKEN_ERROR))
                }
            } catch (e: Exception) {
                Timber.e("GetAccountById error", e)
                Result.failure(getErrorMessage(e))
            }
        }

    override suspend fun createAccount(
        name: String,
        balance: String,
        currency: String
    ): Result<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.createNewAccount(
                        name,
                        balance,
                        currency,
                    )

                    Result.success(response)
                } else {
                    Result.failure(Exception(Variables.TOKEN_ERROR))
                }
            } catch (e: Exception) {
                Timber.e("CreateAccount error", e)
                Result.failure(getErrorMessage(e))
            }
        }

    override suspend fun updateAccountById(account: AccountDetailed): Result<AccountDetailed> =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    val response = accountApi.updateAccountById(
                        id = account.id,
                        name = account.name,
                        balance = account.balance,
                        currency = account.currency,
                    )

                    Result.success(response)
                } else {
                    Result.failure(Exception(Variables.TOKEN_ERROR))
                }
            } catch (e: Exception) {
                Timber.e("UpdateAccountById error", e)
                Result.failure(getErrorMessage(e))
            }
        }

    override suspend fun deleteAccountById(accountId: Int): String? =
        executeOnIO {
            try {
                if (tokenAvaliable) {
                    accountApi.deleteAccountById(accountId)

                    null
                } else {
                    Variables.TOKEN_ERROR
                }
            } catch (e: Exception) {
                val throwable = when (e) {
                    is java.net.UnknownHostException -> Variables.INTERNET_ERROR
                    is java.net.ConnectException -> Variables.INTERNET_ERROR
                    else -> Variables.UNKNOWN_ERROR
                }

                Timber.e("DeleteAccountById error", e)
                throwable
            }
        }
}


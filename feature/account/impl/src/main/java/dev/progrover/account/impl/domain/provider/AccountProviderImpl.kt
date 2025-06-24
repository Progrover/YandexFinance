package dev.progrover.account.impl.domain.provider

import dev.progrover.account.api.domain.AccountIdProvider
import dev.progrover.account.impl.domain.interactor.AccountInteractor
import dev.progrover.account.impl.domain.model.AccountError
import dev.progrover.core.base.di.CoroutineQualifiers
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AccountProviderImpl @Inject constructor(
    private val accountInteractor: AccountInteractor,
    @CoroutineQualifiers.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val exceptionHandler: CoroutineExceptionHandler,
) : AccountIdProvider {

    private val accountId: Int? = null

    override fun getId(scope: CoroutineScope, withResponse: (Result<Int>) -> Unit) {
        scope.launch(dispatcher + exceptionHandler) {
            if (accountId != null) withResponse(Result.success(accountId)) else {
                val response = accountInteractor.getAccounts()
                response.value?.let { list ->
                    try {
                        withResponse(Result.success(list.first().id))
                    } catch (e: Exception) {
                        withResponse(Result.failure(AccountError.NoAccountError))
                    }
                } ?: withResponse(Result.failure(response.error))
            }
        }
    }
}
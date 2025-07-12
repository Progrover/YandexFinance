package dev.progrover.account.impl.domain.provider

import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.impl.domain.interactor.AccountInteractor
import dev.progrover.account.impl.domain.model.AccountAlert
import dev.progrover.core.base.di.CoroutineQualifiers
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AccountPropertiesImpl @Inject constructor(
    private val accountInteractor: AccountInteractor,
    @CoroutineQualifiers.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val exceptionHandler: CoroutineExceptionHandler,
) : AccountPropertiesProvider {

    private var accountId: Int? = null
    private var currency: String? = null

    override fun getId(scope: CoroutineScope, withResponse: (Result<Int>) -> Unit) {
        scope.launch(dispatcher + exceptionHandler) {
            if (accountId != null) {
                withResponse(Result.success(accountId!!))
            } else {
                val response = accountInteractor.getAccounts()
                response.value?.let { list ->
                    try {
                        accountId = list.first().id
                        currency = list.first().currency
                        withResponse(Result.success(accountId!!))
                    } catch (e: Exception) {
                        withResponse(Result.failure(AccountAlert.NoAccountError))
                    }
                } ?: withResponse(Result.failure(response.error))
            }
        }
    }

    override fun getCurrency() : String = currency ?: "???"
    override fun setCurrency(newCurrency: String) {
        currency = newCurrency
    }
}
package dev.progrover.account.impl.domain.provider

import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.AccountDetailed
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

    private var account: AccountDetailed? = null

    override fun getId(scope: CoroutineScope, withResponse: (Result<Int>) -> Unit) {
        scope.launch(dispatcher + exceptionHandler) {
            if (account != null) {
                withResponse(Result.success(account!!.id))
                return@launch
            }

            val response = accountInteractor.getAccounts()
            val list = response.value

            if (!list.isNullOrEmpty()) {
                account = list.first()
                accountInteractor.updateAccount(account!!)
                withResponse(Result.success(account!!.id))
            } else {
                val localResponse = accountInteractor.getAccountsFromLocalStorage()
                val localList = localResponse.value
                if (!localList.isNullOrEmpty()) {
                    account = localList.first()
                    withResponse(Result.success(account!!.id))
                } else {
                    withResponse(Result.failure(response.error))
                }
            }
        }
    }


    override fun getCurrency(): String = account?.currency ?: "???"

    override fun setCurrency(newCurrency: String) {
        account = account?.copy(currency = newCurrency)
    }

    override fun getName(): String =
        account?.name ?: ""

    override fun setUpdatedAccount(newAccount: AccountDetailed) {
        account = newAccount
    }
}
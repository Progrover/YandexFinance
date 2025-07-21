package dev.progrover.account.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.progrover.account.impl.domain.model.AccountAlert
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.account.impl.presentation.contract.balance.BalanceUIEffect
import dev.progrover.account.impl.presentation.contract.balance.BalanceUIEvent
import dev.progrover.account.impl.presentation.contract.balance.BalanceUIState
import dev.progrover.account.impl.presentation.navigation.AccountNavigationFactory.Companion.ACCOUNT_ARG_KEY
import dev.progrover.account.impl.presentation.navigation.BalanceAndNameUpdater
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.LocalStorageError
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.JsonConverter

/**
 * ViewModel, привязанная к currency screen
 */

class BalanceNameViewModel @AssistedInject constructor(
    private val balanceAndNameUpdater: BalanceAndNameUpdater,
    private val accountRepository: AccountRepository,
    private val jsonConverter: JsonConverter,
    @Assisted savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<BalanceUIEvent, BalanceUIState, BalanceUIEffect>(BalanceUIState()) {

    private val accountStr: String? = savedStateHandle[ACCOUNT_ARG_KEY]

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: BalanceUIEvent) {
        when (event) {
            BalanceUIEvent.OnBackClick ->
                setEffect(BalanceUIEffect.NavigateBack)

            is BalanceUIEvent.OnBalanceChange ->
                setState(currentState.copy(account = currentState.account!!.copy(balance = event.newBalance)))

            BalanceUIEvent.OnConfirmClick ->
                sendChanges()

            BalanceUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))

            is BalanceUIEvent.OnNameChange ->
                setState(currentState.copy(account = currentState.account!!.copy(name = event.newName)))
        }
    }

    private fun loadInfo() {
        jsonConverter.fromJson(
            accountStr!!,
            AccountDetailed::class.java,
            null
        )?.let {
            setState(
                currentState.copy(
                    account = it
                )
            )
        } ?: setState(currentState.copy(alert = AccountAlert.BalanceOrNameError))

    }

    private fun sendChanges() {
        tryMultipleLoad(
            function = { accountRepository.updateAccountById(currentState.account!!) },
            onSuccess = {
                saveChangesLocally(true)
                balanceAndNameUpdater.setBalance(currentState.account!!.balance)
                balanceAndNameUpdater.setName(currentState.account!!.name)
                setEffect(BalanceUIEffect.NavigateBack)
            },
            onFailure = {
                setState(currentState.copy(alert = AccountAlert.BalanceOrNameError))
                saveChangesLocally(false)
            }
        )
    }

    private fun saveChangesLocally(synced: Boolean) {
        tryMultipleLoad(
            function = {
                accountRepository.updateAccountByIdFromLocalStorage(
                    currentState.account!!,
                    synced
                )
            },
            onSuccess = {
                balanceAndNameUpdater.setBalance(currentState.account!!.balance)
                balanceAndNameUpdater.setName(currentState.account!!.name)
                setEffect(BalanceUIEffect.NavigateBack)
            },
            onFailure = {
                setState(currentState.copy(alert = LocalStorageError.LocalError))
            }
        )
    }
}
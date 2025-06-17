package dev.progrover.account.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.account.impl.presentation.contract.account.AccountUIEffect
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.Variables
import dev.progrover.shmr_finance.core.uicommon.R
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val prefs: Prefs,
) :
    BaseViewModel<AccountUIEvent, AccountUIState, AccountUIEffect>(AccountUIState()) {

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: AccountUIEvent) =
        when (event) {

            AccountUIEvent.OnAddClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnCurrencyClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnEditClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnTotalAmountClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(error = null))
        }

    private fun loadInfo() {
        setState(currentState.copy(isLoading = true))
        tryMultipleLoad(
            function = {
                accountRepository.getAccounts()
            },
            onSuccess = { result ->
                val currentAccount =
                    result.find { it.id == prefs.getInt(Variables.CURRENT_ACCOUNT_ID) }
                        ?: result.first().let {
                            //Если удалили текущий счет, то перескакиваем первый в списке
                            prefs.putInt(Variables.CURRENT_ACCOUNT_ID, it.id)
                            it
                        }

                setState(
                    currentState.copy(
                        isLoading = false,
                        account = currentAccount,
                    )
                )
            },
            onFailure = { message ->
                setState(
                    currentState.copy(
                        error = message,
                    )
                )
            }
        )
    }
}
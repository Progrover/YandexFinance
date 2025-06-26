package dev.progrover.account.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.impl.domain.model.AccountError
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.account.impl.presentation.contract.account.AccountUIEffect
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.core.uicommon.R
import javax.inject.Inject
/**
 * ViewModel, привязанная к account feature
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
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
                result.firstOrNull()?.let {
                    setState(
                        currentState.copy(
                            isLoading = false,
                            account = it,
                        )
                    )
                } ?: setState(
                    currentState.copy(
                        isLoading = false,
                        error = AccountError.NoAccountError,
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
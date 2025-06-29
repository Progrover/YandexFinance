package dev.progrover.account.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.impl.domain.model.AccountAlert
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.account.impl.presentation.contract.account.AccountUIEffect
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState
import dev.progrover.account.impl.presentation.navigation.CurrencyUpdater
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel, привязанная к account screen
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val currencyUpdater: CurrencyUpdater,
) :
    BaseViewModel<AccountUIEvent, AccountUIState, AccountUIEffect>(AccountUIState()) {

    init {
        startCurrencyUpdater()
        loadInfo()
    }

    override fun handleUIEvent(event: AccountUIEvent) =
        when (event) {
            AccountUIEvent.OnAddClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnCurrencyClick ->
                setEffect(AccountUIEffect.NavigateToCurrencySheet)

            AccountUIEvent.OnEditClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnTotalAmountClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))
        }

    private fun loadInfo() {
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
                        alert = AccountAlert.NoAccountError,
                    )
                )
            },
            onFailure = { message ->
                setState(
                    currentState.copy(
                        alert = message,
                    )
                )
            }
        )
    }

    private fun updateCurrency(newCurrency: String) {
        setState(currentState.copy(isLoading = true))
        currentState.account?.let { currentAccount ->
            tryMultipleLoad(
                function = { accountRepository.updateAccountById(currentAccount.copy(currency = newCurrency)) },
                onSuccess = { newAccount ->
                    setState(
                        currentState.copy(
                            isLoading = false,
                            account = newAccount,
                            alert = AccountAlert.CurrencySuccess,
                        )
                    )
                },
                onFailure = { throwable ->
                    setState(
                        currentState.copy(
                            isLoading = false,
                            alert = throwable as Alert,
                        )
                    )
                }
            )
        } ?: setState(
            currentState.copy(
                isLoading = false,
                alert = AccountAlert.UnableToUpdateCurrencyError
            )
        )
    }

    private fun startCurrencyUpdater() {
        viewModelScope.launch {
            currencyUpdater.currencyUpdateChannel.collectLatest { newCurrency ->
                updateCurrency(newCurrency)
            }
        }
    }
}
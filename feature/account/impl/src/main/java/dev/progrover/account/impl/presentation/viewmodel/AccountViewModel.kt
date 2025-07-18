package dev.progrover.account.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.impl.domain.model.AccountAlert
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.account.impl.presentation.contract.account.AccountUIEffect
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState
import dev.progrover.account.impl.presentation.navigation.BalanceAndNameUpdater
import dev.progrover.account.impl.presentation.navigation.CurrencyUpdater
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.LocalStorageError
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.JsonConverter
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel, привязанная к account screen
 */
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val currencyUpdater: CurrencyUpdater,
    private val accountProvider: AccountPropertiesProvider,
    private val balanceAndNameUpdater: BalanceAndNameUpdater,
    private val jsonConverter: JsonConverter,
) :
    BaseViewModel<AccountUIEvent, AccountUIState, AccountUIEffect>(AccountUIState()) {

    init {
        startBalanceUpdater()
        startNameUpdater()
        startCurrencyUpdater()
        loadInfo()
    }

    override fun handleUIEvent(event: AccountUIEvent) =
        when (event) {
            AccountUIEvent.OnAddClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnCurrencyClick ->
                setEffect(AccountUIEffect.NavigateToCurrencySheet)

            AccountUIEvent.OnEditClick -> {
                currentState.account?.let { account ->
                    setEffect(
                        AccountUIEffect.NavigateToNameAndBalanceScreen(
                            jsonConverter.toJson(
                                account,
                                AccountDetailed::class.java
                            )
                        )
                    )
                } ?: setState(currentState.copy(alert = AccountAlert.NoAccountError))
            }

            AccountUIEvent.OnTotalAmountClick ->
                setEffect(AccountUIEffect.ShowError(R.string.in_develop))

            AccountUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))
        }

    private fun loadInfo() {
        var localLoadingNeeded = false
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
                localLoadingNeeded = true
                setState(
                    currentState.copy(
                        alert = message,
                    )
                )
            }
        )
        if (localLoadingNeeded)
            tryMultipleLoad(
                function = {
                    accountRepository.getAccountsFromLocalStorage()
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
                onFailure = {
                    setState(
                        currentState.copy(
                            alert = LocalStorageError.LocalError,
                        )
                    )
                }
            )
    }

    private fun updateCurrency(newCurrency: String) {
        setState(currentState.copy(isLoading = true))
        currentState.account?.let { currentAccount ->
            var synced = true
            tryMultipleLoad(
                function = { accountRepository.updateAccountById(currentAccount.copy(currency = newCurrency)) },
                onSuccess = { newAccount ->
                    accountProvider.setCurrency(newAccount.currency)
                    setState(
                        currentState.copy(
                            isLoading = false,
                            account = newAccount,
                            alert = AccountAlert.CurrencySuccess,
                        )
                    )
                },
                onFailure = { throwable ->
                    synced = false
                    setState(
                        currentState.copy(
                            isLoading = false,
                            alert = throwable as Alert,
                        )
                    )
                }
            )

            tryMultipleLoad(
                function = {
                    accountRepository.updateAccountByIdFromLocalStorage(
                        currentAccount.copy(
                            currency = newCurrency
                        ),
                        synced
                    )
                },
                onSuccess = { newAccount ->
                    accountProvider.setCurrency(newAccount.currency)
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

    private fun startBalanceUpdater() {
        viewModelScope.launch {
            balanceAndNameUpdater.balanceUpdateChannel.collectLatest { newBalance ->
                setState(
                    currentState.copy(
                        account = currentState.account!!.copy(balance = newBalance),
                        alert = AccountAlert.BalanceOrNameSuccess
                    )
                )
            }
        }
    }

    private fun startNameUpdater() {
        viewModelScope.launch {
            balanceAndNameUpdater.nameUpdateChannel.collectLatest { newName ->
                setState(
                    currentState.copy(
                        account = currentState.account!!.copy(name = newName),
                        alert = AccountAlert.BalanceOrNameSuccess
                    )
                )
            }
        }
    }
}
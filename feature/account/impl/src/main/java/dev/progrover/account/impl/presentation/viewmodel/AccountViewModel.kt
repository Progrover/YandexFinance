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
import dev.progrover.core.base.model.diagrams.BarData
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.JsonConverter
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.core.base.utils.toDatePresentation
import dev.progrover.core.base.utils.toMillis
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
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
        tryMultipleLoad(
            function = {
                accountRepository.getAccounts()
            },
            onSuccess = { result ->
                result.firstOrNull()?.let {
                    setState(
                        currentState.copy(
                            account = it,
                        )
                    )
                    getDataForDiagram(it.id)
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
        )
    }

    private fun updateCurrency(newCurrency: String) {
        setState(currentState.copy(isLoading = true))
        currentState.account?.let { currentAccount ->
            tryMultipleLoad(
                function = { accountRepository.updateAccountById(currentAccount.copy(currency = newCurrency)) },
                onSuccess = { newAccount ->
                    accountProvider.setUpdatedAccount(newAccount)
                    tryMultipleLoad(
                        function = {
                            accountRepository.updateAccountByIdFromLocalStorage(
                                currentAccount.copy(
                                    currency = newCurrency
                                ),
                                true
                            )
                        },
                        onSuccess = {
                            accountProvider.setCurrency(newAccount.currency)
                            setState(
                                currentState.copy(
                                    isLoading = false,
                                    account = newAccount,
                                    alert = AccountAlert.CurrencySuccess,
                                )
                            )
                        },
                        onFailure = {
                            setState(
                                currentState.copy(
                                    isLoading = false,
                                    alert = LocalStorageError.LocalError,
                                )
                            )
                        }
                    )
                },
                onFailure = { throwable ->
                    tryMultipleLoad(
                        function = {
                            accountRepository.updateAccountByIdFromLocalStorage(
                                currentAccount.copy(
                                    currency = newCurrency
                                ),
                                false
                            )
                        },
                        onSuccess = { newAccount ->
                            accountProvider.setUpdatedAccount(newAccount)
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
                accountProvider.setUpdatedAccount(currentState.account!!.copy(balance = newBalance))
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
                accountProvider.setUpdatedAccount(currentState.account!!.copy(balance = newName))
            }
        }
    }

    private fun getDataForDiagram(accountId: Int) {
        tryMultipleLoad(
            function = { accountRepository.getAccountHistory(accountId) },
            onSuccess = { result ->
                val data = result.sortedBy { it.changeTimestamp.toMillis() }.mapNotNull { item ->
                    if (item.newState.balance != item.previousState.balance) {
                        BarData(
                            value = item.newState.balance.toFloat()
                                    - item.previousState.balance.toFloat(),
                            description = item.changeTimestamp
                        )
                    } else null
                }

                setState(
                    currentState.copy(
                        isLoading = false,
                        diagramData = mergeByDate(
                            data,
                            currentState.account?.currency ?: "?"
                        )
                    )
                )
            },
            onFailure = {
                setState(
                    currentState.copy(isLoading = false)
                )
            }
        )
    }
}

fun mergeByDate(list: List<BarData>, currency: String): List<BarData> {
    return list
        .groupBy { bar ->
            Instant.ofEpochMilli(bar.description.toMillis())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
        .map { (date, items) ->
            val totalValue = items.sumOf { it.value.toDouble() }
            val dateMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            BarData(
                value = totalValue.toFloat(),
                description = "${
                    totalValue.toInt().toString().formatToAmount().addCurrency(currency)
                }\n${dateMillis.toDatePresentation()}",
                caption = dateMillis.toDatePresentation()
            )
        }.takeLast(30)
}
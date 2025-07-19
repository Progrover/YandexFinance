package dev.progrover.expenditures.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.LocalStorageError
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.expenditures.impl.domain.model.Expenditure
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEffect
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEvent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIState
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel, привязанная к expenditures feature
 */
class ExpendituresViewModel @Inject constructor(
    private val expendituresRepository: ExpendituresRepository,
    private val idProvider: AccountPropertiesProvider,
    private val transactionsUpdater: TransactionsUpdater,
) :
    BaseViewModel<ExpendituresUIEvent, ExpendituresUIState, ExpendituresUIEffect>(
        ExpendituresUIState()
    ) {

    init {
        subscribeOnTransactionsChanges()
        loadInfo()
    }

    override fun handleUIEvent(event: ExpendituresUIEvent) =
        when (event) {
            ExpendituresUIEvent.OnHistoryClick ->
                setEffect(ExpendituresUIEffect.NavigateToHistoryScreen)

            ExpendituresUIEvent.OnAllExpendituresClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            is ExpendituresUIEvent.OnExpenditureItemClick ->
                setEffect(ExpendituresUIEffect.NavigateToEditTransactionScreen(event.id))

            ExpendituresUIEvent.OnAddExpenditureClick ->
                setEffect(ExpendituresUIEffect.NavigateToCreateTransactionScreen)

            ExpendituresUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))
        }

    private fun loadInfo() {
        setState(currentState.copy(isLoading = true))
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { getExpends(it) },
                onFailure = {
                    setState(
                        currentState.copy(
                            alert = it as Alert,
                        )
                    )
                }
            )
        }
    }

    private fun countTotalAmount(expenditures: List<Expenditure>, currency: String): String {
        try {
            var total = 0.0
            expenditures.forEach { item ->
                total += item.amount.toDouble()
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Expenditures error in countTotalAmount")
            return "???"
        }
    }

    private fun getExpends(accountId: Int) {
        tryMultipleLoad(
            function = {
                expendituresRepository.getExpenditures(
                    accountId
                )
            },
            onSuccess = { result ->

                setState(
                    currentState.copy(
                        isLoading = false,
                        currency = idProvider.getCurrency(),
                        expenditures = result,
                        totalExpenditures = countTotalAmount(result, idProvider.getCurrency()),
                    )
                )
            },
            onFailure = {
                tryMultipleLoad(
                    function = {
                        expendituresRepository.getExpendituresFromLocalStorage(
                            accountId
                        )
                    },
                    onSuccess = { result ->
                        setState(
                            currentState.copy(
                                isLoading = false,
                                currency = idProvider.getCurrency(),
                                expenditures = result,
                                totalExpenditures = countTotalAmount(
                                    result,
                                    idProvider.getCurrency()
                                ),
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

    private fun subscribeOnTransactionsChanges() {
        viewModelScope.launch {
            transactionsUpdater.updateChannel.collectLatest { route ->
                when (route) {
                    RouteDesc.Incomes -> Unit
                    RouteDesc.Expenditures -> loadInfo()
                }
            }
        }
    }
}
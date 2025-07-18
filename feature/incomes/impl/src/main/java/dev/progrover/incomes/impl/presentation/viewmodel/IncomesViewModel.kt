package dev.progrover.incomes.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.incomes.impl.domain.model.Income
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEffect
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEvent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIState
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel, привязанная к incomes feature
 */
class IncomesViewModel @Inject constructor(
    private val incomesRepository: IncomesRepository,
    private val idProvider: AccountPropertiesProvider,
    private val transactionsUpdater: TransactionsUpdater,
) :
    BaseViewModel<IncomesUIEvent, IncomesUIState, IncomesUIEffect>(IncomesUIState()) {

    init {
        subscribeOnTransactionsChanges()
        loadInfo()
    }

    override fun handleUIEvent(event: IncomesUIEvent) =
        when (event) {
            IncomesUIEvent.OnHistoryClick ->
                setEffect(IncomesUIEffect.NavigateToHistoryScreen)

            IncomesUIEvent.OnAllIncomesClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            is IncomesUIEvent.OnIncomeItemClick ->
                setEffect(IncomesUIEffect.NavigateToEditTransactionScreen(event.id))

            IncomesUIEvent.OnAddIncomeClick ->
                setEffect(IncomesUIEffect.NavigateToAddTransactionScreen)

            IncomesUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))
        }

    private fun loadInfo() {
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { getIncomes(it) },
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

    private fun countTotalAmount(expenditures: List<Income>, currency: String): String {
        try {
            var total = 0.0
            expenditures.forEach { item ->
                total += item.amount.toDouble()
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Incomes error in countTotalAmount")
            return "???"
        }
    }

    private fun getIncomes(accountId: Int) {
        tryMultipleLoad(
            function = {
                incomesRepository.getIncomes(accountId)
            },
            onSuccess = { result ->

                setState(
                    currentState.copy(
                        isLoading = false,
                        incomes = result,
                        currency = idProvider.getCurrency(),
                        totalIncomes = countTotalAmount(result, idProvider.getCurrency()),
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

    private fun subscribeOnTransactionsChanges() {
        viewModelScope.launch {
            transactionsUpdater.updateChannel.collectLatest { route ->
                when (route) {
                    RouteDesc.Incomes -> loadInfo()
                    RouteDesc.Expenditures -> Unit
                }
            }
        }
    }
}
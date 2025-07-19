package dev.progrover.history.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.LocalStorageError
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.dateToServerRequest
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.history.impl.domain.model.HistoryAlert
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.history.impl.domain.repository.HistoryRepository
import dev.progrover.history.impl.presentation.contract.history.DatePickerState
import dev.progrover.history.impl.presentation.contract.history.HistoryUIEffect
import dev.progrover.history.impl.presentation.contract.history.HistoryUIEvent
import dev.progrover.history.impl.presentation.contract.history.HistoryUIState
import dev.progrover.history.impl.presentation.navigation.HistoryNavigationFactory.Companion.ARG_KEY_ROUTE
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel, привязанная к history feature
 */
class HistoryViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val historyRepository: HistoryRepository,
    private val idProvider: AccountPropertiesProvider,
    private val transactionsUpdater: TransactionsUpdater,
) :
    BaseViewModel<HistoryUIEvent, HistoryUIState, HistoryUIEffect>(
        HistoryUIState()
    ) {

    private val itemType: RouteDesc = savedStateHandle[ARG_KEY_ROUTE]!!

    init {
        subscribeOnTransactionsChanges()
        loadHistory()
    }

    override fun handleUIEvent(event: HistoryUIEvent) =
        when (event) {
            HistoryUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))

            HistoryUIEvent.OnBackClick ->
                setEffect(HistoryUIEffect.NavigateBack)

            HistoryUIEvent.OnAnalyseClick ->
                setEffect(HistoryUIEffect.ShowError(R.string.in_develop))

            HistoryUIEvent.OnEndClick ->
                setState(
                    currentState.copy(
                        showDatePicker = DatePickerState.EndPick
                    )
                )

            HistoryUIEvent.OnStartClick ->
                setState(
                    currentState.copy(
                        showDatePicker = DatePickerState.StartPick
                    )
                )

            is HistoryUIEvent.OnNewDateSelected -> {
                when (event.type) {
                    DatePickerState.StartPick -> {
                        startLaterThanEndCheck(
                            start = event.date,
                            end = currentState.end
                        ) {
                            setState(
                                currentState.copy(
                                    start = event.date,
                                    showDatePicker = DatePickerState.None
                                )
                            )
                            loadHistory()
                        }
                    }

                    DatePickerState.EndPick -> {
                        startLaterThanEndCheck(currentState.start, event.date) {
                            setState(
                                currentState.copy(
                                    end = event.date,
                                    showDatePicker = DatePickerState.None
                                )
                            )
                            loadHistory()
                        }
                    }

                    DatePickerState.None -> Unit
                }
            }

            HistoryUIEvent.OnDatePickerClose ->
                setState(currentState.copy(showDatePicker = DatePickerState.None))

            is HistoryUIEvent.OnHistoryItemClick ->
                setEffect(
                    HistoryUIEffect.NavigateToEditTransactionScreen(
                        id = event.id,
                        transactionType = itemType
                    )
                )
        }

    private fun loadHistory() {
        setState(currentState.copy(isLoading = true))
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { getHistory(it) },
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

    private fun getHistory(accountId: Int) {
        tryMultipleLoad(
            function = {
                historyRepository.getHistory(
                    accountId,
                    type = itemType,
                    start = currentState.start.dateToServerRequest(),
                    end = currentState.end.dateToServerRequest()
                )
            },
            onSuccess = { result ->
                setState(
                    currentState.copy(
                        isLoading = false,
                        history = result,
                        currency = idProvider.getCurrency(),
                        total = countTotalAmount(result, idProvider.getCurrency()),
                    )
                )
            },
            onFailure = {
                tryMultipleLoad(
                    function = {
                        historyRepository.getHistoryFromLocalStorage(
                            accountId,
                            type = itemType,
                            start = currentState.start.dateToServerRequest(),
                            end = currentState.end.dateToServerRequest()
                        )
                    },
                    onSuccess = { result ->
                        setState(
                            currentState.copy(
                                isLoading = false,
                                history = result,
                                currency = idProvider.getCurrency(),
                                total = countTotalAmount(result, idProvider.getCurrency()),
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

    private fun countTotalAmount(historyItems: List<HistoryElement>, currency: String): String {
        try {
            var total = 0.0
            historyItems.forEach { item ->
                total += item.amount.toDouble()
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Expenditures error in countTotalAmount")
            return "???"
        }
    }

    /**
     * Функция проверяет корректность выбора дат
     */
    private fun startLaterThanEndCheck(start: Long, end: Long, ifNot: () -> Unit) =
        if (start > end) {
            setState(
                currentState.copy(
                    alert = HistoryAlert.IncorrectDataPickError,
                    showDatePicker = DatePickerState.None,
                )
            )
        } else {
            ifNot()
        }

    private fun subscribeOnTransactionsChanges() {
        viewModelScope.launch {
            transactionsUpdater.updateChannel.collectLatest { route ->
                if (route == itemType) loadHistory()
            }
        }
    }
}
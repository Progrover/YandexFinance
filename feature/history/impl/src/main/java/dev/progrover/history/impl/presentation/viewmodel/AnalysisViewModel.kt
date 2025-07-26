package dev.progrover.history.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.LocalStorageError
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.model.diagrams.BarData
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.dateToServerRequest
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.core.base.utils.getEndOfMonth
import dev.progrover.core.base.utils.getStartOfMonth
import dev.progrover.history.impl.domain.model.AnalysisElement
import dev.progrover.history.impl.domain.model.DatePickerState
import dev.progrover.history.impl.domain.model.HistoryAlert
import dev.progrover.history.impl.domain.repository.HistoryRepository
import dev.progrover.history.impl.presentation.contract.analysis.AnalysisUIEffect
import dev.progrover.history.impl.presentation.contract.analysis.AnalysisUIEvent
import dev.progrover.history.impl.presentation.contract.analysis.AnalysisUIState
import dev.progrover.history.impl.presentation.navigation.HistoryNavigationFactory.Companion.ARG_KEY_ROUTE
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel, привязанная к analysis screen
 */
class AnalysisViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val historyRepository: HistoryRepository,
    private val idProvider: AccountPropertiesProvider,
    private val transactionsUpdater: TransactionsUpdater,
) :
    BaseViewModel<AnalysisUIEvent, AnalysisUIState, AnalysisUIEffect>(
        AnalysisUIState()
    ) {

    private val itemType: RouteDesc = savedStateHandle[ARG_KEY_ROUTE]!!

    init {
        subscribeOnTransactionsChanges()
        loadAnalysis()
    }

    override fun handleUIEvent(event: AnalysisUIEvent) =
        when (event) {
            AnalysisUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(alert = null))

            AnalysisUIEvent.OnBackClick ->
                setEffect(AnalysisUIEffect.NavigateBack)

            AnalysisUIEvent.OnAnalyseClick ->
                setEffect(AnalysisUIEffect.ShowError(R.string.in_develop))

            AnalysisUIEvent.OnEndClick ->
                setState(
                    currentState.copy(
                        showDatePicker = DatePickerState.EndPick
                    )
                )

            AnalysisUIEvent.OnStartClick ->
                setState(
                    currentState.copy(
                        showDatePicker = DatePickerState.StartPick
                    )
                )

            is AnalysisUIEvent.OnNewDateSelected -> {
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
                            loadAnalysis()
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
                            loadAnalysis()
                        }
                    }

                    DatePickerState.None -> Unit
                }
            }

            AnalysisUIEvent.OnDatePickerClose ->
                setState(currentState.copy(showDatePicker = DatePickerState.None))

            is AnalysisUIEvent.OnAnalysisItemClick ->
                setEffect(
                    AnalysisUIEffect.NavigateToEditTransactionScreen(
                        id = event.id,
                        transactionType = itemType
                    )
                )

            AnalysisUIEvent.OnChangeDiagramClick ->
                setState(currentState.copy(donutDiagramShown = !currentState.donutDiagramShown))
        }

    private fun loadAnalysis() {
        setState(currentState.copy(isLoading = true))
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { getAnalysis(it) },
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

    private fun getAnalysis(accountId: Int) {
        tryMultipleLoad(
            function = {
                historyRepository.getAnalysis(
                    accountId,
                    type = itemType,
                    start = getStartOfMonth(currentState.start).dateToServerRequest(),
                    end = getEndOfMonth(currentState.end).dateToServerRequest()
                )
            },
            onSuccess = { result ->
                setState(
                    currentState.copy(
                        isLoading = false,
                        analysis = result,
                        barDataList = convertToBarData(result),
                        currency = idProvider.getCurrency(),
                        total = countTotalAmount(result, idProvider.getCurrency()),
                    )
                )
            },
            onFailure = {
                tryMultipleLoad(
                    function = {
                        historyRepository.getAnalysisFromLocalStorage(
                            accountId,
                            type = itemType,
                            start = getStartOfMonth(currentState.start).dateToServerRequest(),
                            end = getEndOfMonth(currentState.end).dateToServerRequest()
                        )
                    },
                    onSuccess = { result ->
                        setState(
                            currentState.copy(
                                isLoading = false,
                                analysis = result,
                                barDataList = convertToBarData(result),
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

    private fun countTotalAmount(historyItems: List<AnalysisElement>, currency: String): String {
        try {
            var total = 0.0
            historyItems.forEach { item ->
                total += if (item.amount.isNotEmpty()) item.amount.toDouble() else 0.0
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Analysis error in countTotalAmount")
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
                if (route == itemType) loadAnalysis()
            }
        }
    }

    private fun convertToBarData(list: List<AnalysisElement>): List<BarData> {
        val groupedList = list.groupBy { it.name }
            .map { (name, group) ->
                val totalValue = group.sumOf { it.percentage }
                BarData(
                    value = totalValue.toFloat(),
                    description = if (totalValue == 0) "<1%" else "$totalValue%",
                    caption = if (totalValue == 0) "<1%" else "$totalValue%",
                    legend = name
                )
            }

        if (groupedList.size < 5) return groupedList.sortedByDescending { it.value }
        else {
            val firstFour = groupedList.sortedByDescending { it.value }.take(4)
            val otherTotal =
                groupedList.sortedByDescending { it.value }.drop(4).sumOf { it.value.toDouble() }
            val lastItem = BarData(
                value = otherTotal.toFloat(),
                caption = if (otherTotal.toInt() == 0) "<1%" else "${otherTotal.toInt()}%",
                description = if (otherTotal.toInt() == 0) "<1%" else "${otherTotal.toInt()}%",
            )
            return firstFour.plus(lastItem)
        }
    }
}
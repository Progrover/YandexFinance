package dev.progrover.history.impl.presentation.contract.history

import dev.progrover.core.base.presentation.mvi.UIEvent

sealed class HistoryUIEvent : UIEvent {
    data object OnStartClick : HistoryUIEvent()
    data object OnEndClick : HistoryUIEvent()
    data object OnAnalyseClick : HistoryUIEvent()
    data object OnBackClick : HistoryUIEvent()
    data object OnErrorDialogDone : HistoryUIEvent()
    data object OnDatePickerClose : HistoryUIEvent()

    class OnNewDateSelected(val date: Long, val type: DatePickerState) : HistoryUIEvent()
}
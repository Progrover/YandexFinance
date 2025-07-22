package dev.progrover.history.impl.presentation.contract.analysis

import dev.progrover.core.base.presentation.mvi.UIEvent
import dev.progrover.history.impl.domain.model.DatePickerState

/**
 * Класс, хранящий все события analysis screen
 */
sealed class AnalysisUIEvent : UIEvent {
    data object OnStartClick : AnalysisUIEvent()
    data object OnEndClick : AnalysisUIEvent()
    data object OnAnalyseClick : AnalysisUIEvent()
    data object OnBackClick : AnalysisUIEvent()
    data object OnErrorDialogDone : AnalysisUIEvent()
    data object OnDatePickerClose : AnalysisUIEvent()

    class OnAnalysisItemClick(val id: Int) : AnalysisUIEvent()
    class OnNewDateSelected(val date: Long, val type: DatePickerState) : AnalysisUIEvent()
}
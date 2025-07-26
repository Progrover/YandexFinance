package dev.progrover.history.impl.presentation.contract.analysis

import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.diagrams.BarData
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.getFirstDayOfCurrentMonthForPresentation
import dev.progrover.core.base.utils.getRealFirstDayOfCurrentMonth
import dev.progrover.history.impl.domain.model.AnalysisElement
import dev.progrover.history.impl.domain.model.DatePickerState

/**
 * Класс, необходимый для отслеживания состояния analysis screen
 */
data class AnalysisUIState(
    val isLoading: Boolean = false,
    val total: String = "",
    val start: Long = getRealFirstDayOfCurrentMonth(),
    val end: Long = getRealFirstDayOfCurrentMonth(),
    val alert: Alert? = null,
    val analysis: List<AnalysisElement> = emptyList(),
    val currency: String = "",
    val showDatePicker: DatePickerState = DatePickerState.None,
    val barDataList: List<BarData>? = null,
    val donutDiagramShown: Boolean = true,
) : UIState {
    // Почему-то DatePicker перескакивает на предыдущий день,
    // если указывать getRealFirstDayOfCurrentMonth()
    val startForPresentation =
        if (start == getRealFirstDayOfCurrentMonth()) {
            getFirstDayOfCurrentMonthForPresentation()
        } else {
            start
        }
    val endForPresentation =
        if (end == getRealFirstDayOfCurrentMonth()) {
            getFirstDayOfCurrentMonthForPresentation()
        } else {
            end
        }
}
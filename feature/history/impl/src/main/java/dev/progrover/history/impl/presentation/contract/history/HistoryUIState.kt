package dev.progrover.history.impl.presentation.contract.history

import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.getFirstDayOfCurrentMonthForPresentation
import dev.progrover.core.base.utils.getRealFirstDayOfCurrentMonth
import dev.progrover.history.impl.domain.model.HistoryElement
/**
 * Класс, необходимый для отслеживания состояния history feature
 */
data class HistoryUIState(
    val isLoading: Boolean = false,
    val total: String = "???",
    val start: Long = getRealFirstDayOfCurrentMonth(),
    val end: Long = System.currentTimeMillis(),
    val error: Error? = null,
    val history: List<HistoryElement> = emptyList(),
    val currency: String = "",
    val showDatePicker: DatePickerState = DatePickerState.None
) : UIState {
     // Почему-то DatePicker перескакивает на предыдущий день,
     // если указывать getRealFirstDayOfCurrentMonth()
    val startForPresentation =
        if (start == getRealFirstDayOfCurrentMonth()) {
            getFirstDayOfCurrentMonthForPresentation()
        } else {
            start
        }
}

enum class DatePickerState {
    StartPick,
    EndPick,
    None,
}
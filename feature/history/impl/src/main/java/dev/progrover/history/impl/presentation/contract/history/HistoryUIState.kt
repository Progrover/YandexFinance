package dev.progrover.history.impl.presentation.contract.history

import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.history.impl.domain.model.HistoryElement
import java.util.Date

data class HistoryUIState(
    val isLoading: Boolean = false,
    val total: String = "???",
    val start: Date = Date(),
    val end: Date = Date(),
    val error: Error? = null,
    val history: List<HistoryElement> = emptyList(),
    val currency: String = "",
) : UIState
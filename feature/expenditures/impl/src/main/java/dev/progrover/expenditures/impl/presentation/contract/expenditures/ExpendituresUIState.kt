package dev.progrover.expenditures.impl.presentation.contract.expenditures

import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.expenditures.impl.domain.model.Expenditure
/**
 * Класс, необходимый для отслеживания состояния expenditures feature
 */
data class ExpendituresUIState(
    val isLoading: Boolean = false,
    val totalExpenditures: String = "",
    val alert: Alert? = null,
    val expenditures: List<Expenditure> = emptyList(),
    val currency: String = "",
) : UIState
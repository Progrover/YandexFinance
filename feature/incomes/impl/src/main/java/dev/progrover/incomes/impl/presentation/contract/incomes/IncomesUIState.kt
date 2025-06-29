package dev.progrover.incomes.impl.presentation.contract.incomes

import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.incomes.impl.domain.model.Income
/**
 * Класс, необходимый для отслеживания состояния incomes feature
 */
data class IncomesUIState(
    val isLoading: Boolean = false,
    val totalIncomes: String = "",
    val incomes: List<Income> = emptyList(),
    val alert: Alert? = null,
    val currency: String = "",
) : UIState
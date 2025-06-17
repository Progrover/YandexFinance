package dev.progrover.incomes.impl.presentation.contract.incomes

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.incomes.impl.domain.model.Income

data class IncomesUIState(
    val isLoading: Boolean = false,
    val totalIncomes: String = "??? ₽",
    val incomes: List<Income> = emptyList(),
    val error: String? = null,
) : UIState
package dev.progrover.incomes.impl.presentation.contract.incomes

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.incomes.impl.domain.model.Income

data class IncomesUIState(
    val isLoading: Boolean = false,
    val totalIncomes: String = "600 000 ₽",
    val incomes: List<Income> = listOf(
        Income(
            id = 1,
            name = "Зарплата",
            amount = "500 000 ₽",
        ),
        Income(
            id = 1,
            name = "Подработка",
            amount = "100 000 ₽",
        ),
    ),
) : UIState
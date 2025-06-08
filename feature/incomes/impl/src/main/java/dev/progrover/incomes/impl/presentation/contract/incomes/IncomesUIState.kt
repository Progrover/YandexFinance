package dev.progrover.incomes.impl.presentation.contract.incomes

import dev.progrover.core.base.presentation.mvi.UIState

data class IncomesUIState(
    val isLoading: Boolean = false,
) : UIState
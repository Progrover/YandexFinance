package dev.progrover.expenditures.impl.presentation.contract.expenditures

import dev.progrover.core.base.presentation.mvi.UIState

data class ExpendituresUIState(
    val isLoading: Boolean = false,
    ) : UIState
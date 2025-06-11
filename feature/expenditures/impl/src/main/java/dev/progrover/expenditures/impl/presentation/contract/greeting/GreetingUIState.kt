package dev.progrover.expenditures.impl.presentation.contract.greeting

import dev.progrover.core.base.presentation.mvi.UIState


data class GreetingUIState(val isLoading: Boolean = false) : UIState
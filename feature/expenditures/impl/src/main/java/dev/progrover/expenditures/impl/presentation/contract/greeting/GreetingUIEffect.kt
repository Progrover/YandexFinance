package dev.progrover.expenditures.impl.presentation.contract.greeting

import dev.progrover.core.base.presentation.mvi.UIEffect

sealed class GreetingUIEffect : UIEffect {
    data object NavigateToExpendituresScreen : GreetingUIEffect()
}
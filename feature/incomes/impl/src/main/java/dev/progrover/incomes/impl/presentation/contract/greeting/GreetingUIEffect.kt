package dev.progrover.incomes.impl.presentation.contract.greeting

import dev.progrover.core.base.presentation.mvi.UIEffect

sealed class GreetingUIEffect : UIEffect {
    data object NavigateToIncomesScreen : GreetingUIEffect()
}
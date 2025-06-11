package dev.progrover.expenditures.impl.presentation.contract.greeting
import dev.progrover.core.base.presentation.mvi.UIEvent

sealed class GreetingUIEvent : UIEvent {
    data object OnAnimationEnd : GreetingUIEvent()
}
package dev.progrover.incomes.impl.presentation.contract.greeting
import dev.progrover.core.base.presentation.mvi.UIState


data class GreetingUIState(
    val progress: Float = 0f,
    val delayInterval: Long = 50,
    val isTextVisible: Boolean = false,
    val isLogoVisible: Boolean = false,
) : UIState {

    val formattedProgress: String = "${(progress * 100).toInt()}%"
}
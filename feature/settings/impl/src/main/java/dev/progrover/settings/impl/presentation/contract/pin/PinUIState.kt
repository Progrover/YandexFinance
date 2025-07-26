package dev.progrover.settings.impl.presentation.contract.pin

import dev.progrover.core.base.presentation.mvi.UIState

/**
 * Класс, необходимый для отслеживания состояния pin screen
 */
data class PinUIState(
    val isLoading: Boolean = false,
    val pinCodeModeOn: Boolean = false,
    val pinCodeIsInSystem: Boolean,
) : UIState
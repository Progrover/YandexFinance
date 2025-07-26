package dev.progrover.settings.impl.presentation.contract.color

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.ColorVariant

/**
 * Класс, необходимый для отслеживания состояния colors screen
 */
data class ColorUIState(
    val isLoading: Boolean = false,
    val currentChoice: ColorVariant? = null,
) : UIState
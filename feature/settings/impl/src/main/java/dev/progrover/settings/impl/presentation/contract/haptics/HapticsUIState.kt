package dev.progrover.settings.impl.presentation.contract.haptics

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.ColorVariant
import dev.progrover.core.base.utils.HapticsVariant

/**
 * Класс, необходимый для отслеживания состояния colors screen
 */
data class HapticsUIState(
    val isLoading: Boolean = false,
    val currentChoice: HapticsVariant? = null,
    val choiseChanged: Boolean = false,
) : UIState
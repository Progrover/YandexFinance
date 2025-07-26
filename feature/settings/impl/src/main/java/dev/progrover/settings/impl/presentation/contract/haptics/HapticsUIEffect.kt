package dev.progrover.settings.impl.presentation.contract.haptics

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты colors screen
 */
sealed class HapticsUIEffect : UIEffect {
    data object NavigateBack : HapticsUIEffect()
}
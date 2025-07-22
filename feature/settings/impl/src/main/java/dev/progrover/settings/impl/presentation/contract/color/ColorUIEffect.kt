package dev.progrover.settings.impl.presentation.contract.color

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты colors screen
 */
sealed class ColorUIEffect : UIEffect {
    data object NavigateBack : ColorUIEffect()
}
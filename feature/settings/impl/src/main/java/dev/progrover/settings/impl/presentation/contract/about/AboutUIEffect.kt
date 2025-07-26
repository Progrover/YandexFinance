package dev.progrover.settings.impl.presentation.contract.about

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты about screen
 */
sealed class AboutUIEffect : UIEffect {
    data object NavigateBack : AboutUIEffect()
}
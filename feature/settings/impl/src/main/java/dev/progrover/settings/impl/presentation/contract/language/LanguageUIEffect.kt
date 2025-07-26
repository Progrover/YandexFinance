package dev.progrover.settings.impl.presentation.contract.language

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты colors screen
 */
sealed class LanguageUIEffect : UIEffect {
    data object NavigateBack : LanguageUIEffect()
}
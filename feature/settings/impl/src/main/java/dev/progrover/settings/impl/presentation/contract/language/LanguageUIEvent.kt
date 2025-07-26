package dev.progrover.settings.impl.presentation.contract.language

import dev.progrover.core.base.presentation.mvi.UIEvent
import dev.progrover.core.base.utils.LocaleVariant

/**
 * Класс, хранящий все события colors screen
 */
sealed class LanguageUIEvent : UIEvent {
    data object OnBackClick : LanguageUIEvent()

    class OnLanguageClick(val newLanguage: LocaleVariant) : LanguageUIEvent()
}
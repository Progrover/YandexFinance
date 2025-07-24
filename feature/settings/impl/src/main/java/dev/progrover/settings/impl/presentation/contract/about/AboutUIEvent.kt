package dev.progrover.settings.impl.presentation.contract.about

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события about screen
 */
sealed class AboutUIEvent : UIEvent {
    data object OnBackClick : AboutUIEvent()
}
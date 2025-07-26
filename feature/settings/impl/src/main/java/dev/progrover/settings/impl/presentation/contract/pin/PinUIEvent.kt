package dev.progrover.settings.impl.presentation.contract.pin

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события pin screen
 */
sealed class PinUIEvent : UIEvent {
    data object OnBackClick : PinUIEvent()
    data object OnNoPinClick : PinUIEvent()
    data object OnPinClick : PinUIEvent()
    data object OnChangePinClick : PinUIEvent()
}
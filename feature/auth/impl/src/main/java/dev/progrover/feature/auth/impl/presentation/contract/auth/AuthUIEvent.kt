package dev.progrover.feature.auth.impl.presentation.contract.auth

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события auth screen
 */
sealed class AuthUIEvent : UIEvent {
    data object OnBackClick : AuthUIEvent()
    data object OnErrorDialogDone : AuthUIEvent()

    class OnFirstPinCodeChange(val newPinCode: Int) : AuthUIEvent()
    class OnSecondPinCodeChange(val newSecondPinCode: Int) : AuthUIEvent()
}
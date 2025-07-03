package dev.progrover.account.impl.presentation.contract.balance

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события balance screen
 */
sealed class BalanceUIEvent : UIEvent {
    class OnNameChange(val newName: String) : BalanceUIEvent()
    class OnBalanceChange(val newBalance: String) : BalanceUIEvent()

    data object OnErrorDialogDone : BalanceUIEvent()
    data object OnBackClick : BalanceUIEvent()
    data object OnConfirmClick : BalanceUIEvent()
}
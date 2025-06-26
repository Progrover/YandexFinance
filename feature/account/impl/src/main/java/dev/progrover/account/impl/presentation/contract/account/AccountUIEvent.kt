package dev.progrover.account.impl.presentation.contract.account

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события account feature
 */
sealed class AccountUIEvent : UIEvent {
    data object OnEditClick : AccountUIEvent()
    data object OnTotalAmountClick : AccountUIEvent()
    data object OnAddClick : AccountUIEvent()
    data object OnCurrencyClick : AccountUIEvent()
    data object OnErrorDialogDone : AccountUIEvent()
}
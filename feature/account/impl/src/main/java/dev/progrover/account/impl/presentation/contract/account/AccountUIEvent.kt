package dev.progrover.account.impl.presentation.contract.account

import dev.progrover.core.base.presentation.mvi.UIEvent

sealed class AccountUIEvent : UIEvent {
    data object OnEditClick: AccountUIEvent()
    data object OnTotalAmountClick: AccountUIEvent()
    data object OnAddClick: AccountUIEvent()
    data object OnCurrencyClick: AccountUIEvent()
}
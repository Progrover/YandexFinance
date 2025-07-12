package dev.progrover.account.impl.presentation.contract.currency

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события currency screen
 */
sealed class CurrencyUIEvent : UIEvent {
    class OnItemClick(val newCurrency: String) : CurrencyUIEvent()

    data object OnCancelClick : CurrencyUIEvent()
}
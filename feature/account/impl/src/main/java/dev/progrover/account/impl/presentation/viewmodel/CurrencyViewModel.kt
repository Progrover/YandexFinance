package dev.progrover.account.impl.presentation.viewmodel

import dev.progrover.account.impl.presentation.contract.currency.CurrencyUIEffect
import dev.progrover.account.impl.presentation.contract.currency.CurrencyUIEvent
import dev.progrover.account.impl.presentation.contract.currency.CurrencyUIState
import dev.progrover.account.impl.presentation.navigation.CurrencyUpdater
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * ViewModel, привязанная к currency screen
 */
class CurrencyViewModel @Inject constructor(
    private val currencyUpdater: CurrencyUpdater,
) :
    BaseViewModel<CurrencyUIEvent, CurrencyUIState, CurrencyUIEffect>(CurrencyUIState()) {

    override fun handleUIEvent(event: CurrencyUIEvent) {
        when (event) {
            CurrencyUIEvent.OnCancelClick ->
                setEffect(CurrencyUIEffect.NavigateBack)

            is CurrencyUIEvent.OnItemClick -> {
                currencyUpdater.setCurrency(event.newCurrency)
                setEffect(CurrencyUIEffect.NavigateBack)
            }
        }
    }
}
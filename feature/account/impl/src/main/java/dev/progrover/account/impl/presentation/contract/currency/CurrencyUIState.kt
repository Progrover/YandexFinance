package dev.progrover.account.impl.presentation.contract.currency

import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.shmr_finance.feature.account.impl.R

/**
 * Класс, необходимый для отслеживания состояния currency screen
 */
data class CurrencyUIState(
    val currencyList: List<Pair<String, Int>> = listOf(
        "RUB" to R.string.rubble,
        "USD" to R.string.dollar,
        "EUR" to R.string.euro,
    ),
    val alert: Alert? = null,
) : UIState
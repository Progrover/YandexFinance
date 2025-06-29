package dev.progrover.account.impl.presentation.contract.currency

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты currency screen
 */
sealed class CurrencyUIEffect : UIEffect {
    data object NavigateBack : CurrencyUIEffect()
}
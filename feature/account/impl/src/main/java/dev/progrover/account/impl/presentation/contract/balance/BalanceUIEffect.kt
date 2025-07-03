package dev.progrover.account.impl.presentation.contract.balance

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты balance screen
 */
sealed class BalanceUIEffect : UIEffect {
    data object NavigateBack : BalanceUIEffect()
}
package dev.progrover.account.impl.presentation.contract.account

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты account feature
 */
sealed class AccountUIEffect : UIEffect {
    class ShowError(@StringRes val messageResId: Int) : AccountUIEffect()
    class NavigateToNameAndBalanceScreen(val accountStr: String) : AccountUIEffect()

    data object NavigateToCurrencySheet : AccountUIEffect()
}
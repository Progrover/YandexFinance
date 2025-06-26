package dev.progrover.incomes.impl.presentation.contract.incomes

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты incomes feature
 */
sealed class IncomesUIEffect : UIEffect {
    data object NavigateToHistoryScreen : IncomesUIEffect()
    class ShowError(@StringRes val messageResId: Int) : IncomesUIEffect()
}
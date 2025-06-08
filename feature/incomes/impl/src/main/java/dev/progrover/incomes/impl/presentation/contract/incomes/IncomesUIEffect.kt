package dev.progrover.incomes.impl.presentation.contract.incomes

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

sealed class IncomesUIEffect : UIEffect {

    class ShowError(@StringRes val messageResId: Int) : IncomesUIEffect()
}
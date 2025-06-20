package dev.progrover.expenditures.impl.presentation.contract.expenditures

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

sealed class ExpendituresUIEffect : UIEffect {
    data object NavigateToHistoryScreen : ExpendituresUIEffect()

    class ShowError(@StringRes val messageResId: Int) : ExpendituresUIEffect()
}
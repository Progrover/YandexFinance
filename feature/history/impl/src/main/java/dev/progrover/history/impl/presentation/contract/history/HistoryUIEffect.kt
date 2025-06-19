package dev.progrover.history.impl.presentation.contract.history

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

sealed class HistoryUIEffect : UIEffect {
    data object NavigateBack : HistoryUIEffect()

    class ShowError(@StringRes val messageResId: Int) : HistoryUIEffect()
}
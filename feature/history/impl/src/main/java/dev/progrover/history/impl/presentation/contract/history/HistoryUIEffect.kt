package dev.progrover.history.impl.presentation.contract.history

import androidx.annotation.StringRes
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты history feature
 */
sealed class HistoryUIEffect : UIEffect {
    data object NavigateBack : HistoryUIEffect()

    class NavigateToEditTransactionScreen(val id: Int, val transactionType: RouteDesc) : HistoryUIEffect()
    class ShowError(@StringRes val messageResId: Int) : HistoryUIEffect()
}
package dev.progrover.history.impl.presentation.contract.analysis

import androidx.annotation.StringRes
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты analysis screen
 */
sealed class AnalysisUIEffect : UIEffect {
    data object NavigateBack : AnalysisUIEffect()

    class NavigateToEditTransactionScreen(val id: Int, val transactionType: RouteDesc) : AnalysisUIEffect()
    class ShowError(@StringRes val messageResId: Int) : AnalysisUIEffect()
}
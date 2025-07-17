package dev.progrover.expenditures.impl.presentation.contract.expenditures

import androidx.annotation.StringRes
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты expenditures feature
 */
sealed class ExpendituresUIEffect : UIEffect {
    data object NavigateToHistoryScreen : ExpendituresUIEffect()
    data object NavigateToCreateTransactionScreen : ExpendituresUIEffect()

    class NavigateToEditTransactionScreen(val id: Int) : ExpendituresUIEffect()
    class ShowError(@StringRes val messageResId: Int) : ExpendituresUIEffect()
}
package dev.progrover.expenditures.impl.presentation.contract.expenditures

import dev.progrover.core.base.presentation.mvi.UIEvent

sealed class ExpendituresUIEvent : UIEvent {
    class OnIncomeItemClick(val id: Int): ExpendituresUIEvent()

    data object OnRefreshClick : ExpendituresUIEvent()
    data object OnAllExpendituresClick: ExpendituresUIEvent()
    data object OnAddIncomeClick: ExpendituresUIEvent()
}
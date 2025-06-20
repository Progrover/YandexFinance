package dev.progrover.incomes.impl.presentation.contract.incomes

import dev.progrover.core.base.presentation.mvi.UIEvent

sealed class IncomesUIEvent : UIEvent {
    class OnIncomeItemClick(val id: Int): IncomesUIEvent()

    data object OnHistoryClick : IncomesUIEvent()
    data object OnAllIncomesClick: IncomesUIEvent()
    data object OnAddIncomeClick: IncomesUIEvent()
    data object OnErrorDialogDone: IncomesUIEvent()
}
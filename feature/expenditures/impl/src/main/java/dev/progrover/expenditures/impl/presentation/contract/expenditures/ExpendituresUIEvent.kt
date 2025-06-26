package dev.progrover.expenditures.impl.presentation.contract.expenditures

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события expenditures feature
 */
sealed class ExpendituresUIEvent : UIEvent {
    class OnExpenditureItemClick(val id: Int) : ExpendituresUIEvent()

    data object OnHistoryClick : ExpendituresUIEvent()
    data object OnAllExpendituresClick : ExpendituresUIEvent()
    data object OnAddExpenditureClick : ExpendituresUIEvent()
    data object OnErrorDialogDone : ExpendituresUIEvent()
}
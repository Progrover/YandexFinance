package dev.progrover.feature.edit.impl.presentation.contract.edit

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события edit feature
 */
sealed class EditUIEvent : UIEvent {
    data object OnTickClick : EditUIEvent()
    data object OnCrossClick : EditUIEvent()
    data object OnDeleteClick : EditUIEvent()
    data object OnCategoryClick : EditUIEvent()
    data object OnAlertDialogDone : EditUIEvent()
    data object OnDatePickerClose : EditUIEvent()
    data object OnDateClick : EditUIEvent()
    data object OnCategoriesListClose: EditUIEvent()

    class OnAmountChange(val newAmount: String) : EditUIEvent()
    class OnDateChange(val newDate: Long) : EditUIEvent()
    class OnTimeChange(val newTime: String) : EditUIEvent()
    class OnCommentChange(val newComment: String) : EditUIEvent()
    class OnNewCategory(val newCategoryId: Int) : EditUIEvent()
}
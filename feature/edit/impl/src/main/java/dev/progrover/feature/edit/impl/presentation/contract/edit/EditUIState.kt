package dev.progrover.feature.edit.impl.presentation.contract.edit

import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.Category
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatInputAsTime
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.core.base.utils.getCurrentDayForPicker
import dev.progrover.feature.edit.api.model.EditVatiant
import dev.progrover.feature.edit.impl.domain.model.EditTransaction

/**
 * Класс, необходимый для отслеживания состояния edit feature
 */
data class EditUIState(
    val isLoading: Boolean = false,
    val actionType: EditVatiant,
    val transactionType: RouteDesc,
    val transaction: EditTransaction? = null,
    val currency: String = "",
    val alert: Alert? = null,
    val showDatePicker: Boolean = false,
    val showCategoryList: Boolean = false,
    val categories: List<Category> = emptyList(),
    val transactionTime: String = "",
) : UIState {
    val amountPresented = transaction?.amount.let { amount ->
        when (amount) {
            null -> "".addCurrency(currency)
            "" -> "0".formatToAmount().addCurrency(currency)
            else -> amount.formatToAmount().addCurrency(currency)
        }
    }
    val transactionTimePresented = transactionTime.formatInputAsTime()
    val dateForPicker = when (transaction?.dateTime) {
        null ->
            getCurrentDayForPicker()

        -1L ->
            getCurrentDayForPicker()

        else ->
            transaction.dateTime
    }
}
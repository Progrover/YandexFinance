package dev.progrover.expenditures.impl.presentation.contract.expenditures

import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.expenditures.api.domain.model.Expenditure

data class ExpendituresUIState(
    val isLoading: Boolean = false,
    val totalExpenditures: String = "??? ₽",
    val error: Error? = null,
    val expenditures: List<Expenditure> = emptyList(),
    val currency: String = "",
) : UIState
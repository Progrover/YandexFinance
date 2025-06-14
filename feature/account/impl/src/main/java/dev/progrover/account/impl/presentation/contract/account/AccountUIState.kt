package dev.progrover.account.impl.presentation.contract.account

import dev.progrover.core.base.presentation.mvi.UIState

data class AccountUIState(
    val isLoading: Boolean = false,
    val currency: String = "₽",
    val totalAmount: String = "-670 000 ₽",
) : UIState
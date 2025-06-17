package dev.progrover.account.impl.presentation.contract.account

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.presentation.mvi.UIState

data class AccountUIState(
    val isLoading: Boolean = false,
    val account: AccountDetailed? = null,
    val error: String? = null,
) : UIState
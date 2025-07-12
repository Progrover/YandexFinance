package dev.progrover.account.impl.presentation.contract.balance

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.mvi.UIState

/**
 * Класс, необходимый для отслеживания состояния balance screen
 */
data class BalanceUIState(
    val isLoading: Boolean = false,
    val account: AccountDetailed? = null,
    val alert: Alert? = null,
) : UIState
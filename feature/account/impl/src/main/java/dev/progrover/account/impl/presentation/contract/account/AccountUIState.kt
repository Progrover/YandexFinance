package dev.progrover.account.impl.presentation.contract.account

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.diagrams.BarData
import dev.progrover.core.base.presentation.mvi.UIState
/**
 * Класс, необходимый для отслеживания состояния account feature
 */
data class AccountUIState(
    val isLoading: Boolean = false,
    val account: AccountDetailed? = null,
    val diagramData: List<BarData>? = null,
    val alert: Alert? = null,
) : UIState
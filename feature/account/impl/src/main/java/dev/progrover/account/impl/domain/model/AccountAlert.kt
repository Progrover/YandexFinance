package dev.progrover.account.impl.domain.model

import androidx.annotation.StringRes
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.AlertType
import dev.progrover.shmr_finance.feature.account.impl.R

sealed class AccountAlert(
    @StringRes override val messageId: Int,
    override val type: AlertType = AlertType.Error,
) : Alert(messageId, type) {

    data object NoAccountError : AccountAlert(R.string.no_account_error)
    data object UnableToUpdateCurrencyError : AccountAlert(R.string.currency_error)
    data object CurrencySuccess : AccountAlert(
        R.string.currency_complete,
        AlertType.Success
    )
}
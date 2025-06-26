package dev.progrover.account.impl.domain.model

import androidx.annotation.StringRes
import dev.progrover.core.base.model.Error
import dev.progrover.shmr_finance.feature.account.impl.R

sealed class AccountError(
    @StringRes override val messageId: Int
) : Error(messageId) {

    data object NoAccountError : AccountError(R.string.no_account_error)
}
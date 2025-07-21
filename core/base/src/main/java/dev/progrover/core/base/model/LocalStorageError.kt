package dev.progrover.core.base.model

import androidx.annotation.StringRes
import dev.progrover.shmr_finance.core.base.R

sealed class LocalStorageError(
    @StringRes override val messageId: Int,
) : Alert(messageId) {
    data object LocalError : LocalStorageError(R.string.no_data_error)
}
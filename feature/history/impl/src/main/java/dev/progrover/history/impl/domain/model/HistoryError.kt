package dev.progrover.history.impl.domain.model

import androidx.annotation.StringRes
import dev.progrover.core.base.model.Error
import dev.progrover.shmr_finance.feature.history.impl.R

sealed class HistoryError(
    @StringRes override val messageId: Int
) : Error(messageId) {

    data object IncorrectDataPickError : HistoryError(R.string.data_error)
}
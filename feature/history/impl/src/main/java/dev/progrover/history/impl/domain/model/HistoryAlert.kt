package dev.progrover.history.impl.domain.model

import androidx.annotation.StringRes
import dev.progrover.core.base.model.Alert
import dev.progrover.shmr_finance.feature.history.impl.R

sealed class HistoryAlert(
    @StringRes override val messageId: Int
) : Alert(messageId) {

    data object IncorrectDataPickError : HistoryAlert(R.string.data_error)
}
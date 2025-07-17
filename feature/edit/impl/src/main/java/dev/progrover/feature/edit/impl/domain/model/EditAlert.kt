package dev.progrover.feature.edit.impl.domain.model

import androidx.annotation.StringRes
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.AlertType
import dev.progrover.shmr_finance.feature.edit.impl.R

sealed class EditAlert(
    @StringRes override val messageId: Int,
    override val type: AlertType = AlertType.Error,
) : Alert(messageId, type) {

    data object DeleteSuccess : EditAlert(
        R.string.delete_success,
        AlertType.Success
    )

    data object AddSuccess : EditAlert(
        R.string.add_success,
        AlertType.Success
    )

    data object UpdateSuccess : EditAlert(
        R.string.change_success,
        AlertType.Success
    )

    data object TimeError : EditAlert(
        R.string.incorrect_time_error,
        AlertType.Error
    )

    data object IncorrectDataError : EditAlert(
        R.string.incorrect_data_error,
        AlertType.Error,
    )
}
package dev.progrover.feature.auth.impl.domain.model

import androidx.annotation.StringRes
import dev.progrover.core.base.model.Alert
import dev.progrover.shmr_finance.feature.auth.impl.R

sealed class AuthAlert(
    @StringRes override val messageId: Int
) : Alert(messageId) {

    data object IncorrectPinCodeError : AuthAlert(R.string.incorrect_pin_error)
    data object IncorrectSecondPinCodeError : AuthAlert(R.string.incorrect_second_pin_error)
}
package dev.progrover.core.base.model

import androidx.annotation.StringRes
import dev.progrover.shmr_finance.core.base.R

sealed class ServerError(
    @StringRes override val messageId: Int,
) : Alert(messageId) {
    data object Error_400 : ServerError(R.string.error_400)

    data object Error_401 : ServerError(R.string.error_401)

    data object Error_404 : ServerError(R.string.error_404)

    data object Error_409 : ServerError(R.string.error_409)

    data object Error_429 : ServerError(R.string.error_429)

    data object Error_500 : ServerError(R.string.error_500)

    data object InternetError : ServerError(R.string.internet_error)

    data object UnknownError : ServerError(R.string.unknown_error)

    data object TokenError : ServerError(R.string.token_error)

    data object MultipleLoadsError : ServerError(R.string.multiple_loads_error)
}
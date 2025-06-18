package dev.progrover.core.base.model

import androidx.annotation.StringRes
import dev.progrover.shmr_finance.core.base.R

sealed class Error(
    @StringRes val message: Int,
) {
    data object Error_400 : Error(R.string.error_400)

    data object Error_401 : Error(R.string.error_401)

    data object Error_404 : Error(R.string.error_404)

    data object Error_409 : Error(R.string.error_409)

    data object Error_429 : Error(R.string.error_429)

    data object Error_500 : Error(R.string.error_500)

    data object InternetError : Error(R.string.internet_error)

    data object UnknownError : Error(R.string.unknown_error)

    data object TokenError : Error(R.string.token_error)

    data object MultipleLoadsError : Error(R.string.multiple_loads_error)
}
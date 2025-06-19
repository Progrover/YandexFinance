package dev.progrover.core.base.model

import androidx.annotation.StringRes

/**
 * Класс для связывания ошибок
 */
open class Error(
   @StringRes open val message: Int,
)
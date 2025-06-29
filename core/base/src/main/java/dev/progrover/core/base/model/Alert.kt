package dev.progrover.core.base.model

import androidx.annotation.StringRes

/**
 * Класс для связывания ошибок
 */
open class Alert(
    @StringRes open val messageId: Int,
    open val type: AlertType = AlertType.Error,
) : Throwable()

enum class AlertType {
    Error,
    Success,
}
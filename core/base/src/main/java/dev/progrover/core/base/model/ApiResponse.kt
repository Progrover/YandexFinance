package dev.progrover.core.base.model
/**
 * Модель получения ответа от сервера
 */
data class ApiResponse<out T>(
    val code: Int = 0,
    val value: T? = null,
    val error: ServerError = ServerError.TokenError,
)

package dev.progrover.core.base.model


data class ApiResponse<out T>(
    val code: Int = 0,
    val value: T? = null,
    val error: Error = Error.TokenError,
)

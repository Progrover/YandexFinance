package dev.progrover.account.impl.data.model

/**
 * Класс для работы с api
 */
data class ManageAccountRequest(
    val name: String,
    val balance: String,
    val currency: String,
)
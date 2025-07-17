package dev.progrover.feature.edit.impl.domain.model

/**
 * Domain модель для экранов добавления и редактирования транзакции
 */
data class EditTransaction(
    val id: Int? = null,
    val accountId: Int,
    val accountName: String,
    val categoryId: Int,
    val comment: String? = null,
    val amount: String,
    val dateTime: Long,
)

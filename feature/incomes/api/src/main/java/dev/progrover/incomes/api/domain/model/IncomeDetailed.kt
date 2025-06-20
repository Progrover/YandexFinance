package dev.progrover.incomes.api.domain.model

data class IncomeDetailed(
    val id: Int,
    val emoji: String? = null,
    val name: String,
    val comment: String? = null,
    val amount: String,
    val dateTime: Long,
)

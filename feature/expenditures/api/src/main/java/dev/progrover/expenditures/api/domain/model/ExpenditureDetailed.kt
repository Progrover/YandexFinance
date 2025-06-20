package dev.progrover.expenditures.api.domain.model

data class ExpenditureDetailed(
    val id: Int,
    val emoji: String? = null,
    val name: String,
    val comment: String? = null,
    val amount: String,
    val dateTime: Long,
)

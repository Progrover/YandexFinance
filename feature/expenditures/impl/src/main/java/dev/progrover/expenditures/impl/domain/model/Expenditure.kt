package dev.progrover.expenditures.impl.domain.model

data class Expenditure(
    val id: Int,
    val emoji: String? = null,
    val name: String,
    val comment: String? = null,
    val amount: String,
)

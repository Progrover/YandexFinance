package dev.progrover.incomes.impl.domain.model

data class Income(
    val id: Int,
    val emoji: String? = null,
    val name: String,
    val comment: String? = null,
    val amount: String,
    )

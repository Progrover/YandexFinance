package dev.progrover.core.base.model

data class Category(
    val id: Int,
    val name: String,
    val emoji: String? = null,
    val inIncome: Boolean,
)

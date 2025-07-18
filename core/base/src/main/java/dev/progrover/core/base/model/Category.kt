package dev.progrover.core.base.model

data class Category(
    override val id: Int,
    override val name: String,
    val emoji: String? = null,
    val isIncome: Boolean,
) : ListItem(
    id,
    name,
    caption = emoji,
)

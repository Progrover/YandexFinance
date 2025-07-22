package dev.progrover.history.impl.domain.model

data class AnalysisElement(
    val id: Int,
    val emoji: String?,
    val name: String,
    val comment: String?,
    val amount: String,
    val dateTime: Long,
    val percentage: Int = 0,
)

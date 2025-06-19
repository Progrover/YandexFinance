package dev.progrover.history.impl.domain.model

/**
 * Понятно, что с точки зрения ДЗ такой подход может быть излишним,
 * но я закладываюсь на то, что в будущем придется добавлять новый функционал,
 * поэтому данный экран должен иметь свою доменную модельку для отображения контента
 */
data class HistoryElement(
    val id: Int,
    val emoji: String?,
    val name: String,
    val comment: String?,
    val amount: String,
    val dateTime: Long,
)

package dev.progrover.articles.impl.presentation.contract.articles

import dev.progrover.core.base.model.Category
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.mvi.UIState

/**
 * Класс, необходимый для отслеживания состояния articles feature
 */
data class ArticlesUIState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val allArticles: List<Category> = emptyList(),
    // Тк у нас конечное количество статей => отображение без пагинации,
    // приходится вводить дополнительную переменную для отображения
    // отсортированного списка
    val articlesForPresentation: List<Category> = emptyList(),
    val alert: Alert? = null,
) : UIState
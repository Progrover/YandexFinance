package dev.progrover.articles.impl.presentation.contract.articles

import dev.progrover.core.base.model.Category
import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.mvi.UIState

data class ArticlesUIState(
    val isLoading: Boolean = false,
    val articles: List<Category> = emptyList(),
    val error: Error? = null,
) : UIState
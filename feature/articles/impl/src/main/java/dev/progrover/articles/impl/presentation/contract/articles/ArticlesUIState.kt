package dev.progrover.articles.impl.presentation.contract.articles

import dev.progrover.core.base.presentation.mvi.UIState

data class ArticlesUIState(
    val isLoading: Boolean = false,

    ) : UIState
package dev.progrover.articles.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEffect
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEvent
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIState
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.core.uicommon.R
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor() :
    BaseViewModel<ArticlesUIEvent, ArticlesUIState, ArticlesUIEffect>(ArticlesUIState()) {

    override fun handleUIEvent(event: ArticlesUIEvent) =
        when (event) {
            is ArticlesUIEvent.OnArticleClick ->
                setEffect(ArticlesUIEffect.ShowError(R.string.in_develop))

            ArticlesUIEvent.OnFindArticleClick ->
                setEffect(ArticlesUIEffect.ShowError(R.string.in_develop))
        }
}
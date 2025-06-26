package dev.progrover.articles.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEffect
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEvent
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIState
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * ViewModel, привязанная к articles feature
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) :
    BaseViewModel<ArticlesUIEvent, ArticlesUIState, ArticlesUIEffect>(ArticlesUIState()) {

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: ArticlesUIEvent) =
        when (event) {
            is ArticlesUIEvent.OnArticleClick ->
                setEffect(ArticlesUIEffect.ShowError(R.string.in_develop))

            ArticlesUIEvent.OnFindArticleClick ->
                setEffect(ArticlesUIEffect.ShowError(R.string.in_develop))

            ArticlesUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(error = null))
        }

    private fun loadInfo() {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))

            tryMultipleLoad(
                function = { articlesRepository.getArticles() },
                onSuccess = { result ->
                    setState(
                        currentState.copy(
                            isLoading = false,
                            articles = result
                        )
                    )
                },
                onFailure = { message ->
                    setState(currentState.copy(error = message))
                },
            )
        }
    }
}
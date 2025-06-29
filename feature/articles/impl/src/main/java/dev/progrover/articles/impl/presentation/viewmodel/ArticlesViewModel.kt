package dev.progrover.articles.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEffect
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEvent
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIState
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel, привязанная к articles feature
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val exceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
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
                setState(currentState.copy(alert = null))

            is ArticlesUIEvent.OnSearchTextChange -> {
                setState(currentState.copy(searchText = event.newText))
                updateArticles(event.newText)
            }
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
                            allArticles = result,
                            articlesForPresentation = result,
                        )
                    )
                },
                onFailure = { message ->
                    setState(currentState.copy(alert = message))
                },
            )
        }
    }

    private fun updateArticles(requestContent: String) {
        viewModelScope.launch(exceptionHandler + ioDispatcher) {
            setState(
                currentState.copy(
                    articlesForPresentation = currentState.allArticles.filter { article ->
                        article.name.contains(requestContent.trim(), ignoreCase = true)
                    }
                )
            )
        }
    }
}
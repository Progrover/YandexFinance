package dev.progrover.articles.impl.presentation.contract.articles

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события articles feature
 */
sealed class ArticlesUIEvent : UIEvent {
    class OnArticleClick(val id: Int) : ArticlesUIEvent()

    data object OnFindArticleClick : ArticlesUIEvent()
    data object OnErrorDialogDone : ArticlesUIEvent()
}
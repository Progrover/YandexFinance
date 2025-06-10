package dev.progrover.articles.impl.domain.interactor

import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import javax.inject.Inject

class ArticlesInteractorImpl @Inject constructor(
    private val repository: ArticlesRepository,
) : ArticlesInteractor {

}
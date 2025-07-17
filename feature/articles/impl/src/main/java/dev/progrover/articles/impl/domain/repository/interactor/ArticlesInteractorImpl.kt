package dev.progrover.articles.impl.domain.repository.interactor

import dev.progrover.articles.api.domain.interactor.ArticlesInteractor
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.Category
import javax.inject.Inject

class ArticlesInteractorImpl @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) : ArticlesInteractor {
    override suspend fun getCategoriesByType(isIncome: Boolean): ApiResponse<List<Category>> =
        articlesRepository.getArticlesByType(isIncome)
}
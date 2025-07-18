package dev.progrover.articles.api.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.Category

interface ArticlesInteractor {
    suspend fun getCategoriesByType(isIncome: Boolean): ApiResponse<List<Category>>

    suspend fun getCategoriesByTypeFromLocalStorage(isIncome: Boolean): ApiResponse<List<Category>>
}
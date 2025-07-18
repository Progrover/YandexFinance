package dev.progrover.articles.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.Category

interface ArticlesRepository {
    suspend fun getArticles(): ApiResponse<List<Category>>
    suspend fun getArticlesByType(isIncome: Boolean): ApiResponse<List<Category>>
    suspend fun getArticlesFromLocalStorage(): ApiResponse<List<Category>>
    suspend fun getArticlesByTypeFromLocalStorage(isIncome: Boolean): ApiResponse<List<Category>>
}
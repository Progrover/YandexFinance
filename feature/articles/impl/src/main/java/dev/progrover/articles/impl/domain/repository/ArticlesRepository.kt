package dev.progrover.articles.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.Category

interface ArticlesRepository {
    suspend fun getArticles(): ApiResponse<List<Category>>
}
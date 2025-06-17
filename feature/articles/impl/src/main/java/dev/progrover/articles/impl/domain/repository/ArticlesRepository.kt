package dev.progrover.articles.impl.domain.repository

import dev.progrover.core.base.model.Category

interface ArticlesRepository {
    suspend fun getArticles(): Result<List<Category>>
}
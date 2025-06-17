package dev.progrover.articles.impl.data.api

import dev.progrover.core.base.model.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {
    @GET("categories")
    suspend fun getArticles(): List<Category>

    @GET("categories/type/{isIncome}")
    suspend fun getArticlesByType(
        @Path("isIncome") isIncome: Boolean
    ): List<Category>
}
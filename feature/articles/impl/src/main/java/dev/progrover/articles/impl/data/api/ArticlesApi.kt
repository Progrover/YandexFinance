package dev.progrover.articles.impl.data.api

import dev.progrover.core.base.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {
    @GET("categories")
    suspend fun getArticles(): Response<List<Category>>

    @GET("categories/type/{isIncome}")
    suspend fun getArticlesByType(
        @Path("isIncome") isIncome: Boolean
    ): Response<List<Category>>
}
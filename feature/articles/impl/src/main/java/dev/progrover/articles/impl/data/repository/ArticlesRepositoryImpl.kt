package dev.progrover.articles.impl.data.repository

import dev.progrover.articles.impl.data.api.ArticlesApi
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val articlesApi: ArticlesApi,
    private val localArticleProvider: LocalArticleProvider,
) : ArticlesRepository, BaseRepository(
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
) {
    override suspend fun getArticles(): ApiResponse<List<Category>> =
        try {
            if (tokenAvaliable) {
                val response = articlesApi.getArticles()

                if (response.isSuccessful) {
                    ApiResponse(value = response.body())
                } else {
                    ApiResponse(code = response.code())
                }
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("GetArticles error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun getArticlesByType(isIncome: Boolean): ApiResponse<List<Category>> =
        try {
            if (tokenAvaliable) {
                val response = articlesApi.getArticlesByType(isIncome)

                if (response.isSuccessful) {
                    ApiResponse(value = response.body())
                } else {
                    ApiResponse(code = response.code())
                }
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("GetArticlesByType error", e)
            ApiResponse(error = getErrorMessage(e))
        }

    override suspend fun getArticlesFromLocalStorage(): ApiResponse<List<Category>> =
        try {
            if (tokenAvaliable) {
                val response = localArticleProvider.getAllCategories()

                ApiResponse(value = response)
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("GetArticles locally error", e)
            ApiResponse(error = getErrorMessage(e))
        }


    override suspend fun getArticlesByTypeFromLocalStorage(isIncome: Boolean): ApiResponse<List<Category>> =
        try {
            if (tokenAvaliable) {
                val response = localArticleProvider.getCategoriesByType(isIncome)

                ApiResponse(value = response)
            } else {
                ApiResponse()
            }
        } catch (e: Exception) {
            Timber.e("GetArticlesByType locally error", e)
            ApiResponse(error = getErrorMessage(e))
        }
}
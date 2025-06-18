package dev.progrover.articles.impl.data.repository

import dev.progrover.articles.impl.data.api.ArticlesApi
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
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
}
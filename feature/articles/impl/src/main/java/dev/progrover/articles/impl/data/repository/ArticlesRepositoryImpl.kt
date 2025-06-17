package dev.progrover.articles.impl.data.repository

import dev.progrover.articles.impl.data.api.ArticlesApi
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.Category
import dev.progrover.core.base.utils.Variables
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
    override suspend fun getArticles(): Result<List<Category>> =
        try {
            if (tokenAvaliable) {
                val response = articlesApi.getArticles()

                Result.success(response)
            } else {
                Result.failure(Exception(Variables.TOKEN_ERROR))
            }
        } catch (e: Exception) {
            Timber.e("GetArticles error", e)
            Result.failure(getErrorMessage(e))
        }
}
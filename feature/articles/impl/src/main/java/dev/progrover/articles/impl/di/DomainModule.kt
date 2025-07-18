package dev.progrover.articles.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.articles.api.domain.interactor.ArticlesInteractor
import dev.progrover.articles.impl.data.api.ArticlesApi
import dev.progrover.articles.impl.data.repository.ArticlesRepositoryImpl
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.articles.impl.domain.repository.interactor.ArticlesInteractorImpl
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesArticlesRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        articlesApi: ArticlesApi,
    ): ArticlesRepository =
        ArticlesRepositoryImpl(
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            articlesApi = articlesApi,
        )

    @Provides
    @Singleton
    fun providesArticleInteractor(
        articlesRepository: ArticlesRepository,
    ): ArticlesInteractor =
        ArticlesInteractorImpl(
            articlesRepository,
        )
}
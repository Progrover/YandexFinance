package dev.progrover.articles.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.articles.impl.data.repository.ArticlesRepositoryImpl
import dev.progrover.articles.impl.domain.interactor.ArticlesInteractor
import dev.progrover.articles.impl.domain.interactor.ArticlesInteractorImpl
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideArticlesRepository(
        prefs: Prefs,
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
    ): ArticlesRepository =
        ArticlesRepositoryImpl(
            prefs = prefs,
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
        )

    @Provides
    @Singleton
    fun provideArticlesInteractor(
        repository: ArticlesRepository,
    ): ArticlesInteractor =
        ArticlesInteractorImpl(
            repository = repository,
        )
}
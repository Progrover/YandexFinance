package dev.progrover.articles.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.articles.impl.data.repository.ArticlesRepositoryImpl
import dev.progrover.articles.impl.domain.interactor.ArticlesInteractor
import dev.progrover.articles.impl.domain.interactor.ArticlesInteractorImpl
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideArticlesRepository(
    ): ArticlesRepository =
        ArticlesRepositoryImpl(
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
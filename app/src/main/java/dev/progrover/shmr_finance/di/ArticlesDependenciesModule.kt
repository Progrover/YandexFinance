package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.articles.api.domain.interactor.ArticlesInteractor
import dev.progrover.articles.impl.di.DaggerArticlesComponent
import dev.progrover.core.base.di.BaseComponent
import dev.progrover.core.base.di.BaseDependencies

@Module
class ArticlesDependenciesModule {

    @Provides
    fun provideArticlesDependencies(
        baseComponent: BaseDependencies
    ): ArticlesDependencies {
        return DaggerArticlesComponent.factory().create(baseComponent)
    }

    @Provides
    fun provideArticlesInteractor(
        accountDependencies: ArticlesDependencies
    ): ArticlesInteractor =
        accountDependencies.articlesInteractor()
}
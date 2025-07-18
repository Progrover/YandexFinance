package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.articles.impl.di.DaggerArticlesComponent
import dev.progrover.core.base.di.BaseComponent

@Module
class ArticlesDependenciesModule {

    @Provides
    fun provideArticlesDependencies(
        baseComponent: BaseComponent
    ): ArticlesDependencies {
        return DaggerArticlesComponent.factory().create(baseComponent)
    }
}
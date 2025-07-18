package dev.progrover.articles.impl.di

import dagger.Component
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.core.base.di.BaseDependencies
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ArticlesViewModelModule::class,
        DomainModule::class,
        ArticlesNavigationModule::class,
        NetworkModule::class,
    ],
    dependencies = [BaseDependencies::class]
)
interface ArticlesComponent : ArticlesDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            baseDependencies: BaseDependencies,
        ): ArticlesComponent
    }

    fun getArticlesViewModelFactory(): ViewModelFactory
}



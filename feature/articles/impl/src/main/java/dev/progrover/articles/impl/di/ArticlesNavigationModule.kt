package dev.progrover.articles.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.articles.impl.presentation.navigation.ArticlesNavigationFactory
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory

@Module
interface ArticlesNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindArticlesNavigationFactory(factory: ArticlesNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
package dev.progrover.history.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.history.impl.presentation.navigation.HistoryNavigationFactory

@Module
interface HistoryNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindHistoryNavigationFactory(factory: HistoryNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
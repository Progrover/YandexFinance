package dev.progrover.history.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.history.impl.presentation.navigation.HistoryNavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {

    @Singleton
    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindHistoryNavigationFactory(factory: HistoryNavigationFactory): NavigationFactory
}
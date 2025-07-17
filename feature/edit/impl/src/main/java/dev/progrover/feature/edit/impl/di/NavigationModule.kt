package dev.progrover.feature.edit.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.feature.edit.impl.presentation.navigation.EditNavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {

    @Singleton
    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindEditNavigationFactory(factory: EditNavigationFactory): NavigationFactory
}
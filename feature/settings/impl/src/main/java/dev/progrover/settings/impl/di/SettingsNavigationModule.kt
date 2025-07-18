package dev.progrover.settings.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.settings.impl.presentation.navigation.SettingsNavigationFactory

@Module
interface SettingsNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindSettingsNavigationFactory(factory: SettingsNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
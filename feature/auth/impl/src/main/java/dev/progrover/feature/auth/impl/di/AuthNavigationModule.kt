package dev.progrover.feature.auth.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.feature.auth.impl.presentation.navigation.AuthNavigationFactory

@Module
interface AuthNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindAuthNavigationFactory(factory: AuthNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
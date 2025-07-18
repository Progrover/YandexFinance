package dev.progrover.feature.edit.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.feature.edit.impl.presentation.navigation.EditNavigationFactory

@Module
interface EditNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindEditNavigationFactory(factory: EditNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
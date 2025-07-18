package dev.progrover.expenditures.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.expenditures.impl.presentation.navigation.ExpendituresNavigationFactory

@Module
interface ExpendituresNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindExpendituresNavigationFactory(factory: ExpendituresNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
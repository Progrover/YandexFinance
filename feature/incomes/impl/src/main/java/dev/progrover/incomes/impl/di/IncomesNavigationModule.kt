package dev.progrover.incomes.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.incomes.impl.presentation.navigation.IncomesNavigationFactory

@Module
interface IncomesNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindIncomesNavigationFactory(factory: IncomesNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
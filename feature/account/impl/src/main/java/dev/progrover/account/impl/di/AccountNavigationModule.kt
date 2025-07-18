package dev.progrover.account.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dev.progrover.account.impl.presentation.navigation.AccountNavigationFactory
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory

@Module
interface AccountNavigationModule {

    @Binds
    @IntoSet
    @NavigationFactoryQualifiers.MainActivity
    fun bindAccountNavigationFactory(factory: AccountNavigationFactory): @JvmSuppressWildcards NavigationFactory
}
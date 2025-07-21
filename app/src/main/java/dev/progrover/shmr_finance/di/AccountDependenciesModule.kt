package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.account.impl.di.AccountComponent
import dev.progrover.account.impl.di.DaggerAccountComponent
import dev.progrover.core.base.di.BaseDependencies
import javax.inject.Singleton

@Module
class AccountDependenciesModule {


    private var cachedComponent: AccountComponent? = null

    @Provides
    @Singleton
    fun provideAccountDependencies(
        baseComponent: BaseDependencies
    ): AccountDependencies {
        if (cachedComponent == null) {
            cachedComponent = DaggerAccountComponent.factory().create(baseComponent)
        }
        return cachedComponent!!
    }

    @Provides
    @Singleton
    fun provideAccountComponent(
        baseDependencies: BaseDependencies
    ): AccountComponent {
        if (cachedComponent == null) {
            cachedComponent = DaggerAccountComponent.factory().create(baseDependencies)
        }
        return cachedComponent!!
    }

    @Provides
    @Singleton
    fun provideAccountInteractor(
        accountDependencies: AccountDependencies
    ): AccountInteractor =
        accountDependencies.accountInteractor()

    @Provides
    @Singleton
    fun providePropertiesProvider(
        accountDependencies: AccountDependencies
    ): AccountPropertiesProvider =
        accountDependencies.propertiesProvider()
}
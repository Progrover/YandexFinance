package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.account.impl.di.DaggerAccountComponent
import dev.progrover.core.base.di.BaseComponent
import dev.progrover.core.base.di.BaseDependencies

@Module
class AccountDependenciesModule {

    @Provides
    fun provideAccountDependencies(
        baseComponent: BaseDependencies
    ): AccountDependencies {
        return DaggerAccountComponent.factory().create(baseComponent)
    }

    @Provides
    fun provideAccountInteractor(
        accountDependencies: AccountDependencies
    ): AccountInteractor =
        accountDependencies.accountInteractor()

    @Provides
    fun providePropertiesProvider(
        accountDependencies: AccountDependencies
    ): AccountPropertiesProvider =
        accountDependencies.propertiesProvider()
}
package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.account.impl.di.DaggerAccountComponent
import dev.progrover.core.base.di.BaseComponent

@Module
class AccountDependenciesModule {

    @Provides
    fun provideAccountDependencies(
        baseComponent: BaseComponent
    ): AccountDependencies {
        return DaggerAccountComponent.factory().create(baseComponent)
    }
}
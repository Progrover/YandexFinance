package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseComponent
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.expenditures.impl.di.DaggerExpendituresComponent

@Module
class ExpendituresDependenciesModule {

    @Provides
    fun provideExpendituresDependencies(
        baseComponent: BaseDependencies,
        accountDependencies: AccountDependencies,
    ): ExpendituresDependencies {
        return DaggerExpendituresComponent.factory().create(baseComponent, accountDependencies)
    }
}
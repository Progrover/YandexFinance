package dev.progrover.shmr_finance.di

import dagger.Module
import dagger.Provides
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseComponent
import dev.progrover.incomes.api.di.IncomesDependencies
import dev.progrover.incomes.impl.di.DaggerIncomesComponent

@Module
class IncomesDependenciesModule {

    @Provides
    fun provideIncomesDependencies(
        baseComponent: BaseComponent,
        accountDependencies: AccountDependencies,
    ): IncomesDependencies {
        return DaggerIncomesComponent.factory().create(baseComponent, accountDependencies)
    }
}
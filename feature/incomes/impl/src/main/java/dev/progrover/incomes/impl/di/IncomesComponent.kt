package dev.progrover.incomes.impl.di

import dagger.Component
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.incomes.api.di.IncomesDependencies
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        IncomesViewModelModule::class,
        DomainModule::class,
        IncomesNavigationModule::class,
        DataModule::class,
    ],
    dependencies = [
        BaseDependencies::class,
        AccountDependencies::class,
    ]
)
interface IncomesComponent : IncomesDependencies {
    fun getIncomesViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            baseDependencies: BaseDependencies,
            accountDependencies: AccountDependencies,
        ): IncomesComponent
    }
}



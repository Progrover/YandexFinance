package dev.progrover.expenditures.impl.di

import dagger.Component
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.expenditures.api.di.ExpendituresDependencies

@ExpendituresScope
@Component(
    modules = [
        ExpendituresViewModelModule::class,
        DomainModule::class,
        DataModule::class,
        ExpendituresNavigationModule::class,
    ],
    dependencies = [
        BaseDependencies::class,
        AccountDependencies::class,
    ]
)
interface ExpendituresComponent : ExpendituresDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            baseDependencies: BaseDependencies,
            accountDependencies: AccountDependencies,
        ): ExpendituresComponent
    }

    fun getExpendituresViewModelFactory(): ViewModelFactory
}



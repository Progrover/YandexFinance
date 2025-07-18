package dev.progrover.history.impl.di

import dagger.Component
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.incomes.api.di.IncomesDependencies

@HistoryScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        HistoryNavigationModule::class,
    ],
    dependencies = [
        BaseDependencies::class,
        AccountDependencies::class,
        ExpendituresDependencies::class,
        IncomesDependencies::class,
    ]
)
internal interface HistoryComponent {
    @Component.Factory
    interface Factory {
        fun create(
            baseDependencies: BaseDependencies,
            accountDependencies: AccountDependencies,
            expendituresDependencies: ExpendituresDependencies,
            incomesDependencies: IncomesDependencies,
        ): HistoryComponent
    }

    fun getHistoryViewModelFactory(): HistoryViewModelAssistedFactory
}



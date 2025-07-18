package dev.progrover.history.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.history.impl.di.DaggerHistoryComponent
import dev.progrover.history.impl.di.HistoryComponent
import dev.progrover.incomes.api.di.IncomesDependencies

@Composable
internal fun HistoryComponent(
    accountDependencies: AccountDependencies,
    expendituresDependencies: ExpendituresDependencies,
    incomesDependencies: IncomesDependencies,
): HistoryComponent {
    val context = LocalContext.current.applicationContext
    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerHistoryComponent.factory().create(
            baseDependencies = baseDependencies,
            accountDependencies = accountDependencies,
            expendituresDependencies = expendituresDependencies,
            incomesDependencies = incomesDependencies,
        )
    }
    return component
}
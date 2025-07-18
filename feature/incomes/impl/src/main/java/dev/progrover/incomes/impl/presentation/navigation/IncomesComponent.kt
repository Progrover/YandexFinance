package dev.progrover.incomes.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.incomes.impl.di.DaggerIncomesComponent
import dev.progrover.incomes.impl.di.IncomesComponent

@Composable
internal fun IncomesComponent(
    accountDependencies: AccountDependencies
): IncomesComponent {
    val context = LocalContext.current.applicationContext

    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerIncomesComponent.factory().create(baseDependencies, accountDependencies)
    }
    return component
}
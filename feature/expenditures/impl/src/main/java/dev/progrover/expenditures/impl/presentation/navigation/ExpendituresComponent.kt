package dev.progrover.expenditures.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.expenditures.impl.di.DaggerExpendituresComponent
import dev.progrover.expenditures.impl.di.ExpendituresComponent

@Composable
internal fun ExpendituresComponent(
    accountDependencies: AccountDependencies
): ExpendituresComponent {
    val context = LocalContext.current.applicationContext
    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerExpendituresComponent.factory().create(baseDependencies, accountDependencies)
    }
    return component
}
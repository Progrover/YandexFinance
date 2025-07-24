package dev.progrover.feature.auth.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.feature.auth.impl.di.AuthComponent
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.feature.auth.impl.di.DaggerAuthComponent

@Composable
internal fun AuthComponent(
    expendituresDependencies: ExpendituresDependencies,
): AuthComponent {
    val context = LocalContext.current.applicationContext
    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerAuthComponent.factory().create(
            baseDependencies = baseDependencies,
            expendituresDependencies = expendituresDependencies,
        )
    }
    return component
}
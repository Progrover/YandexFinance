package dev.progrover.incomes.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.incomes.api.IncomesFeature.INCOMES_SCREEN
import dev.progrover.incomes.api.IncomesFeature.ROUTE_NAME
import dev.progrover.incomes.impl.presentation.screen.IncomesScreen
import dev.progrover.incomes.impl.presentation.viewmodel.IncomesViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации incomes feature
 */
@Singleton
class IncomesNavigationFactory @Inject constructor(
    private val accountDependencies: AccountDependencies
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = INCOMES_SCREEN,
            route = ROUTE_NAME
        ) {
            composable(route = INCOMES_SCREEN) {
                val component = IncomesComponent(
                    accountDependencies = accountDependencies
                )
                val viewModel: IncomesViewModel = viewModel<IncomesViewModel>(
                    factory = component.getIncomesViewModelFactory(),
                )
                IncomesScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
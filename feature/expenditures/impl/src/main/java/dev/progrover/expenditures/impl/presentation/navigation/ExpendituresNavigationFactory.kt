package dev.progrover.expenditures.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.expenditures.api.ExpendituresFeature.EXPENDITURES_SCREEN
import dev.progrover.expenditures.api.ExpendituresFeature.ROUTE_NAME
import dev.progrover.expenditures.impl.presentation.screen.ExpendituresScreen
import dev.progrover.expenditures.impl.presentation.viewmodel.ExpendituresViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации expenditures feature
 */
@Singleton
class ExpendituresNavigationFactory @Inject constructor(
    private val accountDependencies: AccountDependencies,
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = EXPENDITURES_SCREEN,
            route = ROUTE_NAME
        ) {
            composable(route = EXPENDITURES_SCREEN) {
                val component = ExpendituresComponent(accountDependencies)
                val viewModel: ExpendituresViewModel = viewModel<ExpendituresViewModel>(
                    factory = component.getExpendituresViewModelFactory(),
                )
                ExpendituresScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
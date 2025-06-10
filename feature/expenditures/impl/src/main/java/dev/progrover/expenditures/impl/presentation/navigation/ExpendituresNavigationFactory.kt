package dev.progrover.expenditures.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.expenditures.impl.presentation.viewmodel.GreetingViewModel
import dev.progrover.expenditures.api.ExpendituresFeature.EXPENDITURES_SCREEN
import dev.progrover.expenditures.api.ExpendituresFeature.ROUTE_NAME
import dev.progrover.expenditures.api.ExpendituresFeature.GREETING_SCREEN
import dev.progrover.expenditures.impl.presentation.screen.GreetingScreen
import dev.progrover.expenditures.impl.presentation.screen.ExpendituresScreen
import dev.progrover.expenditures.impl.presentation.viewmodel.ExpendituresViewModel
import javax.inject.Inject

class ExpendituresNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = GREETING_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = EXPENDITURES_SCREEN) {
                val viewModel: ExpendituresViewModel = hiltViewModel()
                ExpendituresScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = GREETING_SCREEN) {
                val viewModel: GreetingViewModel = hiltViewModel()
                GreetingScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}
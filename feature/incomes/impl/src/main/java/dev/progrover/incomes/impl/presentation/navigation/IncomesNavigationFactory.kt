package dev.progrover.incomes.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.incomes.impl.presentation.viewmodel.GreetingViewModel
import dev.progrover.incomes.api.IncomesFeature.INCOMES_SCREEN
import dev.progrover.incomes.api.IncomesFeature.ROUTE_NAME
import dev.progrover.incomes.api.IncomesFeature.GREETING_SCREEN
import dev.progrover.incomes.impl.presentation.screen.GreetingScreen
import dev.progrover.incomes.impl.presentation.screen.IncomesScreen
import dev.progrover.incomes.impl.presentation.viewmodel.IncomesViewModel
import javax.inject.Inject

class IncomesNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = GREETING_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = INCOMES_SCREEN) {
                val viewModel: IncomesViewModel = hiltViewModel()
                IncomesScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = GREETING_SCREEN) {
                val viewModel: GreetingViewModel = hiltViewModel()
                GreetingScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}
package dev.progrover.incomes.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.incomes.api.IncomesFeature.INCOMES_SCREEN
import dev.progrover.incomes.api.IncomesFeature.ROUTE_NAME
import dev.progrover.incomes.impl.presentation.screen.IncomesScreen
import dev.progrover.incomes.impl.presentation.viewmodel.IncomesViewModel
import javax.inject.Inject

class IncomesNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = INCOMES_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = INCOMES_SCREEN) {
                val viewModel: IncomesViewModel = hiltViewModel()
                IncomesScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
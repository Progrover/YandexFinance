package dev.progrover.history.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.history.api.HistoryFeature.HISTORY_SCREEN
import dev.progrover.history.api.HistoryFeature.ROUTE_NAME
import dev.progrover.history.impl.presentation.screen.HistoryScreen
import dev.progrover.history.impl.presentation.viewmodel.HistoryViewModel
import javax.inject.Inject
/**
 * Необходим для навигации history feature
 */
class HistoryNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = HISTORY_PAGE,
            route = ROUTE_NAME
        ) {
            composable(
                route = HISTORY_PAGE,
                arguments = listOf(
                    navArgument(ARG_KEY_ROUTE) { type = NavType.EnumType(RouteDesc::class.java) },
                )
            ) {
                val viewModel: HistoryViewModel = hiltViewModel()
                HistoryScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    companion object {
        internal const val ARG_KEY_ROUTE = "argKeyRoute"
        private const val HISTORY_PAGE = "$HISTORY_SCREEN/{$ARG_KEY_ROUTE}"
    }
}
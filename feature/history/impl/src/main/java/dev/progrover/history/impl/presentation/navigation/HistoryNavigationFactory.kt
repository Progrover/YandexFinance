package dev.progrover.history.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.history.api.HistoryFeature.HISTORY_SCREEN
import dev.progrover.history.api.HistoryFeature.ROUTE_NAME
import dev.progrover.history.impl.di.SavedStateViewModelFactory
import dev.progrover.history.impl.presentation.screen.HistoryScreen
import dev.progrover.history.impl.presentation.viewmodel.HistoryViewModel
import dev.progrover.incomes.api.di.IncomesDependencies
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации history feature
 */
@Singleton
class HistoryNavigationFactory @Inject constructor(
    private val accountDependencies: AccountDependencies,
    private val expendituresDependencies: ExpendituresDependencies,
    private val incomesDependencies: IncomesDependencies,
) : NavigationFactory {

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
            ) { entry ->
                val component = HistoryComponent(
                    accountDependencies,
                    expendituresDependencies = expendituresDependencies,
                    incomesDependencies = incomesDependencies
                )
                val viewModel: HistoryViewModel = viewModel<HistoryViewModel>(
                    factory = SavedStateViewModelFactory(
                        assistedFactory = component.getHistoryViewModelFactory(),
                        owner = entry,
                        defaultArgs = entry.arguments
                    ),
                )
                HistoryScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    companion object {
        internal const val ARG_KEY_ROUTE = "argKeyRoute"
        private const val HISTORY_PAGE = "$HISTORY_SCREEN/{$ARG_KEY_ROUTE}"
    }
}
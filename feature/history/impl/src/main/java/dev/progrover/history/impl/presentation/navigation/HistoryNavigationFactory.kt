package dev.progrover.history.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.history.api.HistoryFeature.ANALYSIS_SCREEN
import dev.progrover.history.api.HistoryFeature.HISTORY_SCREEN
import dev.progrover.history.api.HistoryFeature.ROUTE_NAME
import dev.progrover.history.impl.di.DaggerHistoryComponent
import dev.progrover.history.impl.di.SavedStateAnalysisViewModelFactory
import dev.progrover.history.impl.di.SavedStateHistoryViewModelFactory
import dev.progrover.history.impl.presentation.screen.AnalysisScreen
import dev.progrover.history.impl.presentation.screen.HistoryScreen
import dev.progrover.history.impl.presentation.viewmodel.AnalysisViewModel
import dev.progrover.history.impl.presentation.viewmodel.HistoryViewModel
import dev.progrover.incomes.api.di.IncomesDependencies
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации history screen
 */
@Singleton
class HistoryNavigationFactory @Inject constructor(
    private val accountDependencies: AccountDependencies,
    private val expendituresDependencies: ExpendituresDependencies,
    private val incomesDependencies: IncomesDependencies,
    private val baseDependencies: BaseDependencies,
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = HISTORY_PAGE,
            route = ROUTE_NAME
        ) {
            val component =  DaggerHistoryComponent.factory().create(
                baseDependencies = baseDependencies,
                accountDependencies = accountDependencies,
                expendituresDependencies = expendituresDependencies,
                incomesDependencies = incomesDependencies,
            )
            composable(
                route = HISTORY_PAGE,
                arguments = listOf(
                    navArgument(ARG_KEY_ROUTE) { type = NavType.EnumType(RouteDesc::class.java) },
                )
            ) { entry ->
                val viewModel: HistoryViewModel = viewModel<HistoryViewModel>(
                    factory = SavedStateHistoryViewModelFactory(
                        assistedFactory = component.getHistoryViewModelFactory(),
                        owner = entry,
                        defaultArgs = entry.arguments
                    ),
                )
                HistoryScreen(viewModel = viewModel, navController = navController)
            }

            composable(
                route = ANALYSIS_PAGE,
                arguments = listOf(
                    navArgument(ARG_KEY_ROUTE) { type = NavType.EnumType(RouteDesc::class.java) },
                )
            ) { entry ->
                val viewModel: AnalysisViewModel = viewModel<AnalysisViewModel>(
                    factory = SavedStateAnalysisViewModelFactory(
                        assistedFactory = component.getAnalysisViewModelFactory(),
                        owner = entry,
                        defaultArgs = entry.arguments
                    ),
                )
                AnalysisScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    companion object {
        internal const val ARG_KEY_ROUTE = "argKeyRoute"
        private const val HISTORY_PAGE = "$HISTORY_SCREEN/{$ARG_KEY_ROUTE}"
        private const val ANALYSIS_PAGE = "$ANALYSIS_SCREEN/{$ARG_KEY_ROUTE}"
    }
}
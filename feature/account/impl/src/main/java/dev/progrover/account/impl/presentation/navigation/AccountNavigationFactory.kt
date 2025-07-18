package dev.progrover.account.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import dev.progrover.account.api.AccountFeature.ACCOUNT_SCREEN
import dev.progrover.account.api.AccountFeature.BALANCE_NAME_SCREEN
import dev.progrover.account.api.AccountFeature.CURRENCY_SCREEN
import dev.progrover.account.api.AccountFeature.ROUTE_NAME
import dev.progrover.account.impl.di.SavedStateViewModelFactory
import dev.progrover.account.impl.presentation.screen.AccountScreen
import dev.progrover.account.impl.presentation.screen.BalanceNameScreen
import dev.progrover.account.impl.presentation.screen.CurrencyScreen
import dev.progrover.account.impl.presentation.viewmodel.AccountViewModel
import dev.progrover.account.impl.presentation.viewmodel.BalanceNameViewModel
import dev.progrover.account.impl.presentation.viewmodel.CurrencyViewModel
import dev.progrover.core.base.navigation.NavigationFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации account feature
 */
@Singleton
class AccountNavigationFactory @Inject constructor() : NavigationFactory {

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = ACCOUNT_SCREEN,
            route = ROUTE_NAME
        ) {
            composable(
                route = ACCOUNT_SCREEN,
            ) {
                val component = AccountComponent()
                val viewModel: AccountViewModel = viewModel<AccountViewModel>(
                    factory = component.getAccountViewModelFactory(),
                )
                AccountScreen(viewModel = viewModel, navController = navController)
            }

            bottomSheet(route = CURRENCY_SCREEN) {
                val component = AccountComponent()
                val viewModel: CurrencyViewModel = viewModel<CurrencyViewModel>(
                    factory = component.getAccountViewModelFactory(),
                )
                CurrencyScreen(viewModel = viewModel, navController = navController)
            }

            composable(
                route = ACCOUNT_CHANGES_PAGE,
                arguments = listOf(
                    navArgument(ACCOUNT_ARG_KEY) { type = NavType.StringType }
                )
            ) { entry ->
                val component = AccountComponent()
                val viewModel: BalanceNameViewModel = viewModel<BalanceNameViewModel>(
                    factory = SavedStateViewModelFactory(
                        assistedFactory = component.getBalanceViewModelFactory(),
                        owner = entry,
                        defaultArgs = entry.arguments
                    ),
                )
                BalanceNameScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    companion object {
        internal const val ACCOUNT_ARG_KEY = "accountArgKey"
        private const val ACCOUNT_CHANGES_PAGE = "$BALANCE_NAME_SCREEN/{$ACCOUNT_ARG_KEY}"
    }
}
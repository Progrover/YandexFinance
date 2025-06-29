package dev.progrover.account.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import dev.progrover.account.api.AccountFeature.ACCOUNT_SCREEN
import dev.progrover.account.api.AccountFeature.CURRENCY_SCREEN
import dev.progrover.account.api.AccountFeature.ROUTE_NAME
import dev.progrover.account.impl.presentation.screen.AccountScreen
import dev.progrover.account.impl.presentation.screen.CurrencyScreen
import dev.progrover.account.impl.presentation.viewmodel.AccountViewModel
import dev.progrover.account.impl.presentation.viewmodel.CurrencyViewModel
import dev.progrover.core.base.navigation.NavigationFactory
import javax.inject.Inject

/**
 * Необходим для навигации account feature
 */
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
                val viewModel: AccountViewModel = hiltViewModel()
                AccountScreen(viewModel = viewModel, navController = navController)
            }

            bottomSheet(route = CURRENCY_SCREEN) {
                val viewModel: CurrencyViewModel = hiltViewModel()
                CurrencyScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
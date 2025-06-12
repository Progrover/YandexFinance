package dev.progrover.account.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.account.api.AccountFeature.ACCOUNT_SCREEN
import dev.progrover.account.api.AccountFeature.ROUTE_NAME
import dev.progrover.account.impl.presentation.screen.AccountScreen
import dev.progrover.account.impl.presentation.viewmodel.AccountViewModel
import dev.progrover.core.base.navigation.NavigationFactory
import javax.inject.Inject

class AccountNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = ACCOUNT_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = ACCOUNT_SCREEN) {
                val viewModel: AccountViewModel = hiltViewModel()
                AccountScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
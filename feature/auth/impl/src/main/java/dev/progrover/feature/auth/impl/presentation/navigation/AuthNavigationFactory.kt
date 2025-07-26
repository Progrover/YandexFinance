package dev.progrover.feature.auth.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.feature.auth.api.AuthFeature.AUTH_SCREEN
import dev.progrover.feature.auth.api.AuthFeature.ROUTE_NAME
import dev.progrover.feature.auth.api.domain.model.NavigationVariant
import dev.progrover.feature.auth.impl.di.SavedStateAuthViewModelFactory
import dev.progrover.feature.auth.impl.presentation.screen.AuthScreen
import dev.progrover.feature.auth.impl.presentation.viewmodel.AuthViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации auth screen
 */
@Singleton
class AuthNavigationFactory @Inject constructor(
    private val expendituresDependencies: ExpendituresDependencies,
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = PINCODE_PAGE,
            route = ROUTE_NAME
        ) {
            composable(
                route = PINCODE_PAGE,
                arguments = listOf(
                    navArgument(ARG_KEY_TASK) {
                        type = NavType.EnumType(NavigationVariant::class.java)
                    },
                )
            ) { entry ->
                val component = AuthComponent(
                    expendituresDependencies,
                )
                val viewModel: AuthViewModel = viewModel<AuthViewModel>(
                    factory = SavedStateAuthViewModelFactory(
                        assistedFactory = component.getAuthViewModelFactory(),
                        owner = entry,
                        defaultArgs = entry.arguments
                    ),
                )
                AuthScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    companion object {
        internal const val ARG_KEY_TASK = "argKeyTask"
        private const val PINCODE_PAGE = "$AUTH_SCREEN/{$ARG_KEY_TASK}"
    }
}
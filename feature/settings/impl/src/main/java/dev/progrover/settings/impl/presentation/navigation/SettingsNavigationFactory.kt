package dev.progrover.settings.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.settings.api.SettingsFeature.ROUTE_NAME
import dev.progrover.settings.api.SettingsFeature.SETTINGS_SCREEN
import dev.progrover.settings.impl.presentation.screen.SettingsScreen
import dev.progrover.settings.impl.presentation.viewmodel.SettingsViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации settings feature
 */
@Singleton
class SettingsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = SETTINGS_SCREEN,
            route = ROUTE_NAME
        ) {
            composable(route = SETTINGS_SCREEN) {
                val component = SettingsComponent()
                val viewModel: SettingsViewModel = viewModel<SettingsViewModel>(
                    factory = component.getSettingsViewModelFactory()
                )
                SettingsScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
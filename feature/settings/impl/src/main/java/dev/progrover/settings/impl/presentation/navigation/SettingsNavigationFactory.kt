package dev.progrover.settings.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.settings.api.SettingsFeature.ABOUT_SCREEN
import dev.progrover.settings.api.SettingsFeature.COLORS_SCREEN
import dev.progrover.settings.api.SettingsFeature.HAPTICS_SCREEN
import dev.progrover.settings.api.SettingsFeature.LANGUAGE_SCREEN
import dev.progrover.settings.api.SettingsFeature.PIN_SCREEN
import dev.progrover.settings.api.SettingsFeature.ROUTE_NAME
import dev.progrover.settings.api.SettingsFeature.SETTINGS_SCREEN
import dev.progrover.settings.api.SettingsFeature.SYNC_SCREEN
import dev.progrover.settings.impl.di.DaggerSettingsComponent
import dev.progrover.settings.impl.presentation.screen.AboutScreen
import dev.progrover.settings.impl.presentation.screen.ColorScreen
import dev.progrover.settings.impl.presentation.screen.HapticsScreen
import dev.progrover.settings.impl.presentation.screen.LanguageScreen
import dev.progrover.settings.impl.presentation.screen.PinScreen
import dev.progrover.settings.impl.presentation.screen.SettingsScreen
import dev.progrover.settings.impl.presentation.screen.SyncScreen
import dev.progrover.settings.impl.presentation.viewmodel.AboutViewModel
import dev.progrover.settings.impl.presentation.viewmodel.ColorViewModel
import dev.progrover.settings.impl.presentation.viewmodel.HapticsViewModel
import dev.progrover.settings.impl.presentation.viewmodel.LanguageViewModel
import dev.progrover.settings.impl.presentation.viewmodel.PinViewModel
import dev.progrover.settings.impl.presentation.viewmodel.SettingsViewModel
import dev.progrover.settings.impl.presentation.viewmodel.SyncViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации settings feature
 */
@Singleton
class SettingsNavigationFactory @Inject constructor(
    private val baseDependencies: BaseDependencies,
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = SETTINGS_SCREEN,
            route = ROUTE_NAME
        ) {
            val component =
                DaggerSettingsComponent.builder().baseDependencies(baseDependencies).build()

            composable(route = SETTINGS_SCREEN) {
                val viewModel: SettingsViewModel = viewModel<SettingsViewModel>(
                    factory = component.getViewModelFactory()
                )
                SettingsScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = COLORS_SCREEN) {
                val viewModel: ColorViewModel = viewModel<ColorViewModel>(
                    factory = component.getViewModelFactory()
                )
                ColorScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = LANGUAGE_SCREEN) {
                val viewModel: LanguageViewModel = viewModel<LanguageViewModel>(
                    factory = component.getViewModelFactory()
                )
                LanguageScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = HAPTICS_SCREEN) {
                val viewModel: HapticsViewModel = viewModel<HapticsViewModel>(
                    factory = component.getViewModelFactory()
                )
                HapticsScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = PIN_SCREEN) {
                val viewModel: PinViewModel = viewModel<PinViewModel>(
                    factory = component.getViewModelFactory()
                )
                PinScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = ABOUT_SCREEN) {
                val viewModel: AboutViewModel = viewModel<AboutViewModel>(
                    factory = component.getViewModelFactory()
                )
                AboutScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = SYNC_SCREEN) {
                val viewModel: SyncViewModel = viewModel<SyncViewModel>(
                    factory = component.getViewModelFactory()
                )
                SyncScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
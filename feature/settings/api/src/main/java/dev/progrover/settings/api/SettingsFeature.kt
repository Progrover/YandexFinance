package dev.progrover.settings.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate

/**
 * Класс, содержащий варианты навигации к экранам settings feature
 */
object SettingsFeature {

    const val ROUTE_NAME = "settingsFeature"
    const val SETTINGS_SCREEN = "settingsScreenRoute"
    const val COLORS_SCREEN = "colorsScreenRoute"
    const val LANGUAGE_SCREEN = "languageScreenRoute"
    const val HAPTICS_SCREEN = "hapticsScreenRoute"
    const val PIN_SCREEN = "pinScreenRoute"

    fun openSettingsScreen(navController: NavController) =
        navController.customNavigate(SETTINGS_SCREEN)

    fun openColorsScreen(navController: NavController) =
        navController.customNavigate(COLORS_SCREEN)

    fun openLanguageScreen(navController: NavController) =
        navController.customNavigate(LANGUAGE_SCREEN)

    fun openHapticsScreen(navController: NavController) =
        navController.customNavigate(HAPTICS_SCREEN)

    fun openPinScreen(navController: NavController) =
        navController.customNavigate(PIN_SCREEN)
}

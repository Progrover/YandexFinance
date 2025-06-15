package dev.progrover.settings.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate

object SettingsFeature {

    const val ROUTE_NAME = "settingsFeature"
    const val SETTINGS_SCREEN = "settingsScreenRoute"

    fun openSettingsScreen(navController: NavController) =
        navController.customNavigate(SETTINGS_SCREEN)
}

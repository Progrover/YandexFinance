package dev.progrover.settings.api

import androidx.navigation.NavController

object SettingsFeature {

    const val ROUTE_NAME = "settingsFeature"
    const val SETTINGS_SCREEN = "settingsScreenRoute"

    fun openSettingsScreen(navController: NavController) =
        navController.navigate(SETTINGS_SCREEN)
}

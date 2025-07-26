package dev.progrover.feature.auth.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate
import dev.progrover.feature.auth.api.domain.model.NavigationVariant

object AuthFeature {
    const val ROUTE_NAME = "authRouteName"
    const val AUTH_SCREEN = "authScreenName"

    fun openAuthScreen(
        navController: NavController,
        type: NavigationVariant,
    ) =
        navController.customNavigate("$AUTH_SCREEN/$type")
}
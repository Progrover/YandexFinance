package dev.progrover.account.api

import androidx.navigation.NavController

object AccountFeature {

    const val ROUTE_NAME = "accountFeature"
    const val ACCOUNT_SCREEN = "accountScreenRoute"

    fun openAccountScreen(navController: NavController) =
        navController.navigate(ACCOUNT_SCREEN)
}

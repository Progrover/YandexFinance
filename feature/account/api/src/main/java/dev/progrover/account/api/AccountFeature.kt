package dev.progrover.account.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate

object AccountFeature {

    const val ROUTE_NAME = "accountFeature"
    const val ACCOUNT_SCREEN = "accountScreenRoute"

    fun openAccountScreen(navController: NavController) =
        navController.customNavigate(ACCOUNT_SCREEN)
}

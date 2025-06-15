package dev.progrover.expenditures.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate

object ExpendituresFeature {

    const val ROUTE_NAME = "expendituresFeature"
    const val EXPENDITURES_SCREEN = "expendituresScreenRoute"

    fun openExpendituresScreen(navController: NavController) =
        navController.customNavigate(EXPENDITURES_SCREEN)
}
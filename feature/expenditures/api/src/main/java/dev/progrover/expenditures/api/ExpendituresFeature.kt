package dev.progrover.expenditures.api

import androidx.navigation.NavController

object ExpendituresFeature {

    const val ROUTE_NAME = "expendituresFeature"
    const val EXPENDITURES_SCREEN = "expendituresScreenRoute"

    fun openExpendituresScreen(navController: NavController) =
        navController.navigate(EXPENDITURES_SCREEN)
}
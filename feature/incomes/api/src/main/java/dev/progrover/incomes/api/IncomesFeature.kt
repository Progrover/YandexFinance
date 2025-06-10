package dev.progrover.incomes.api

import androidx.navigation.NavController

object IncomesFeature {

    const val ROUTE_NAME = "incomesFeature"
    const val INCOMES_SCREEN = "incomesScreenRoute"

    fun openIncomesScreen(navController: NavController) =
        navController.navigate(INCOMES_SCREEN)
}

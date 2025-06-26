package dev.progrover.incomes.api

import androidx.navigation.NavController
/**
 * Класс, содержащий варианты навигации к экранам incomes feature
 */
object IncomesFeature {

    const val ROUTE_NAME = "incomesFeature"
    const val INCOMES_SCREEN = "incomesScreenRoute"

    fun openIncomesScreen(navController: NavController) =
        navController.navigate(INCOMES_SCREEN) {
            launchSingleTop = true
        }
}
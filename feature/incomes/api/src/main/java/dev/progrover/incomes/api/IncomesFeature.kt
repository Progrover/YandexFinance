package dev.progrover.incomes.api

import androidx.navigation.NavController

object IncomesFeature {

    const val ROUTE_NAME = "incomesFeature"
    const val INCOMES_SCREEN = "incomesScreenRoute"
    const val GREETING_SCREEN = "greetingScreenRoute"


    fun openIncomesScreen(navController: NavController, afterGreeting: Boolean = false) =
        navController.navigate(INCOMES_SCREEN) {
            if (afterGreeting) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
}

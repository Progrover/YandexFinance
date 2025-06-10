package dev.progrover.expenditures.api

import androidx.navigation.NavController

object ExpendituresFeature {

    const val ROUTE_NAME = "expendituresFeature"
    const val EXPENDITURES_SCREEN = "expendituresScreenRoute"
    const val GREETING_SCREEN = "greetingScreenRoute"


    fun openExpendituresScreen(navController: NavController, afterGreeting: Boolean = false) =
        navController.navigate(EXPENDITURES_SCREEN) {
            if (afterGreeting) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
}

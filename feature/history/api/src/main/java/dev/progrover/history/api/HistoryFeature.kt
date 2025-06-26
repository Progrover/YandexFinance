package dev.progrover.history.api

import androidx.navigation.NavController
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.utils.customNavigate
/**
 * Класс, содержащий варианты навигации к экранам history feature
 */
object HistoryFeature {

    const val ROUTE_NAME = "historyFeature"
    const val HISTORY_SCREEN = "historyScreenRoute"

    fun openHistoryScreen(navController: NavController, route: RouteDesc) =
        navController.customNavigate("$HISTORY_SCREEN/$route")
}
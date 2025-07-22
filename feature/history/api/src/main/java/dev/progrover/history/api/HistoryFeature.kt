package dev.progrover.history.api

import androidx.navigation.NavController
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.utils.customNavigate
/**
 * Класс, содержащий варианты навигации к экранам history screen
 */
object HistoryFeature {

    const val ROUTE_NAME = "historyFeature"
    const val HISTORY_SCREEN = "historyScreenRoute"
    const val ANALYSIS_SCREEN = "analysisScreenRoute"

    fun openHistoryScreen(navController: NavController, route: RouteDesc) =
        navController.customNavigate("$HISTORY_SCREEN/$route")

    fun openAnalysisScreen(navController: NavController, route: RouteDesc) =
        navController.customNavigate("$ANALYSIS_SCREEN/$route")
}
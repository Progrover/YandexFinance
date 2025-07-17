package dev.progrover.feature.edit.api

import androidx.navigation.NavController
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.utils.customNavigate
import dev.progrover.feature.edit.api.model.EditVatiant

/**
 * Класс, содержащий варианты навигации к экранам edit feature
 */
object EditFeature {

    const val ROUTE_NAME = "editFeature"
    const val EDIT_SCREEN = "editScreenRoute"

    fun openEditScreen(navController: NavController, transactionType: RouteDesc, transactionId: Int) =
        navController.customNavigate("$EDIT_SCREEN/$transactionType/${EditVatiant.Edit}/$transactionId")

    fun openAddScreen(navController: NavController, transactionType: RouteDesc) =
        navController.customNavigate("$EDIT_SCREEN/$transactionType/${EditVatiant.Add}/-1")
}
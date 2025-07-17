package dev.progrover.feature.edit.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.edit.impl.presentation.screen.EditScreen
import dev.progrover.feature.edit.api.EditFeature.EDIT_SCREEN
import dev.progrover.feature.edit.api.EditFeature.ROUTE_NAME
import dev.progrover.feature.edit.api.model.EditVatiant
import dev.progrover.feature.edit.impl.presentation.viewmodel.EditViewModel
import javax.inject.Inject

/**
 * Необходим для навигации edit feature
 */
class EditNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = EDIT_SCREEN,
            route = ROUTE_NAME
        ) {
            composable(
                route = EDIT_TRANSACTION_PAGE,
                arguments = listOf(
                    navArgument(ARG_KEY_TYPE_TRANSACTION) {
                        type = NavType.EnumType(RouteDesc::class.java)
                    },
                    navArgument(ARG_KEY_TYPE_ACTION) {
                        type = NavType.EnumType(EditVatiant::class.java)
                    },
                    navArgument(ARG_KEY_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                val viewModel: EditViewModel = hiltViewModel()
                EditScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    companion object {
        internal const val ARG_KEY_TYPE_TRANSACTION = "argKeyTypeTransaction"
        internal const val ARG_KEY_TYPE_ACTION = "argKeyTypeAction"
        internal const val ARG_KEY_ID = "argKeyId"
        private const val EDIT_TRANSACTION_PAGE =
            "$EDIT_SCREEN/{$ARG_KEY_TYPE_TRANSACTION}/{$ARG_KEY_TYPE_ACTION}/{$ARG_KEY_ID}"
    }
}
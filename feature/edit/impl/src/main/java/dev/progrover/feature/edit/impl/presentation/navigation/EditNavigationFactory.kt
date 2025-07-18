package dev.progrover.feature.edit.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.edit.impl.presentation.screen.EditScreen
import dev.progrover.feature.edit.api.EditFeature.EDIT_SCREEN
import dev.progrover.feature.edit.api.EditFeature.ROUTE_NAME
import dev.progrover.feature.edit.api.model.EditVatiant
import dev.progrover.feature.edit.impl.di.SavedStateViewModelFactory
import dev.progrover.feature.edit.impl.presentation.viewmodel.EditViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации edit feature
 */
@Singleton
class EditNavigationFactory @Inject constructor(
    private val articlesDependencies: ArticlesDependencies,
    private val accountDependencies: AccountDependencies,
) : NavigationFactory {

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
            ) { entry ->
                val component = EditComponent(
                    articlesDependencies = articlesDependencies,
                    accountDependencies = accountDependencies
                )
                val viewModel: EditViewModel = viewModel<EditViewModel>(
                    factory = SavedStateViewModelFactory(
                        assistedFactory = component.getEditViewModelFactory(),
                        owner = entry,
                        defaultArgs = entry.arguments
                    ),
                )
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
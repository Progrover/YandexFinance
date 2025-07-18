package dev.progrover.articles.impl.presentation.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.progrover.articles.api.ArticlesFeature.ARTICLES_SCREEN
import dev.progrover.articles.api.ArticlesFeature.ROUTE_NAME
import dev.progrover.articles.impl.presentation.screen.ArticlesScreen
import dev.progrover.articles.impl.presentation.viewmodel.ArticlesViewModel
import dev.progrover.core.base.navigation.NavigationFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Необходим для навигации articles feature
 */
@Singleton
class ArticlesNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = ARTICLES_SCREEN,
            route = ROUTE_NAME
        ) {
            composable(route = ARTICLES_SCREEN) {

                val component = ArticlesComponent()
                val viewModel: ArticlesViewModel = viewModel(
                    factory = component.getArticlesViewModelFactory()
                )
                ArticlesScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
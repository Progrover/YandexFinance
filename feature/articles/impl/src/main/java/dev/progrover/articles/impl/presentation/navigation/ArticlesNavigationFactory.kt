package dev.progrover.articles.impl.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
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

class ArticlesNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(
            startDestination = ARTICLES_SCREEN,
            route = ROUTE_NAME
        ) {

            composable(route = ARTICLES_SCREEN) {
                val viewModel: ArticlesViewModel = hiltViewModel()
                ArticlesScreen(viewModel = viewModel, navController = navController)
            }
        }
    }
}
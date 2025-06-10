package dev.progrover.articles.api

import androidx.navigation.NavController

object ArticlesFeature {

    const val ROUTE_NAME = "articlesFeature"
    const val ARTICLES_SCREEN = "articlesScreenRoute"

    fun openArticlesScreen(navController: NavController) =
        navController.navigate(ARTICLES_SCREEN)
}

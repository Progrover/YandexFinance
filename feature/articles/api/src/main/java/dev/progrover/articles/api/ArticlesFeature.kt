package dev.progrover.articles.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate

object ArticlesFeature {

    const val ROUTE_NAME = "articlesFeature"
    const val ARTICLES_SCREEN = "articlesScreenRoute"

    fun openArticlesScreen(navController: NavController) =
        navController.customNavigate(ARTICLES_SCREEN)
}

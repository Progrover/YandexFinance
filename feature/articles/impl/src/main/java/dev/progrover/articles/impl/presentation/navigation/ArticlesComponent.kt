package dev.progrover.articles.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.articles.impl.di.ArticlesComponent
import dev.progrover.articles.impl.di.DaggerArticlesComponent
import dev.progrover.core.base.di.BaseComponentProvider

@Composable
internal fun ArticlesComponent(): ArticlesComponent {
    val context = LocalContext.current.applicationContext
    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerArticlesComponent.factory().create(baseDependencies)
    }
    return component
}
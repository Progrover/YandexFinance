package dev.progrover.feature.edit.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.feature.edit.impl.di.DaggerEditComponent
import dev.progrover.feature.edit.impl.di.EditComponent

@Composable
internal fun EditComponent(
    articlesDependencies: ArticlesDependencies,
    accountDependencies: AccountDependencies
): EditComponent {
    val context = LocalContext.current.applicationContext
    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerEditComponent.factory().create(
            accountDependencies = accountDependencies,
            articlesDependencies = articlesDependencies,
            baseDependencies = baseDependencies,
        )
    }
    return component
}
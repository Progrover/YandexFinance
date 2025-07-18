package dev.progrover.feature.edit.impl.di

import dagger.Component
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.core.base.di.BaseDependencies

@EditScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        EditNavigationModule::class,
    ],
    dependencies = [
        ArticlesDependencies::class,
        BaseDependencies::class,
        AccountDependencies::class,
    ]
)
internal interface EditComponent {
    @Component.Factory
    interface Factory {
        fun create(
            accountDependencies: AccountDependencies,
            articlesDependencies: ArticlesDependencies,
            baseDependencies: BaseDependencies
        ): EditComponent
    }

    fun getEditViewModelFactory(): EditViewModelAssistedFactory
}



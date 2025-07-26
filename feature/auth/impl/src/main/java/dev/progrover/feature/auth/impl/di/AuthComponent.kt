package dev.progrover.feature.auth.impl.di

import dagger.Component
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.expenditures.api.di.ExpendituresDependencies

@AuthScope
@Component(
    modules = [
        AuthNavigationModule::class,
    ],
    dependencies = [
        BaseDependencies::class,
        ExpendituresDependencies::class,
    ]
)
internal interface AuthComponent {
    @Component.Factory
    interface Factory {
        fun create(
            baseDependencies: BaseDependencies,
            expendituresDependencies: ExpendituresDependencies,
        ): AuthComponent
    }

    fun getAuthViewModelFactory(): AuthViewModelAssistedFactory
}



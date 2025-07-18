package dev.progrover.account.impl.di

import dagger.Component
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.core.base.di.BaseDependencies
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CurrencyViewModelModule::class,
        AccountViewModelModule::class,
        DomainModule::class,
        AccountNavigationModule::class,
        NetworkModule::class,
    ],
    dependencies = [BaseDependencies::class]
)
interface AccountComponent : AccountDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            baseDependencies: BaseDependencies,
        ): AccountComponent
    }

    fun getAccountViewModelFactory(): ViewModelFactory

    fun getBalanceViewModelFactory(): BalanceNameViewModelAssistedFactory
}



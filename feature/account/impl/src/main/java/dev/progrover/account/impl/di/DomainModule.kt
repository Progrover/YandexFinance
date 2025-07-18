package dev.progrover.account.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.account.impl.data.api.AccountApi
import dev.progrover.account.impl.data.repository.AccountRepositoryImpl
import dev.progrover.account.impl.domain.interactor.AccountInteractorImpl
import dev.progrover.account.impl.domain.provider.AccountPropertiesImpl
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.account.impl.presentation.navigation.BalanceAndNameUpdater
import dev.progrover.account.impl.presentation.navigation.CurrencyUpdater
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun providesAccountRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        accountApi: AccountApi,
        localAccountProvider: LocalAccountProvider,
    ): AccountRepository =
        AccountRepositoryImpl(
            accountApi = accountApi,
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            localAccountProvider = localAccountProvider,
        )

    @Provides
    @Singleton
    fun providesAccountInteractor(
        repository: AccountRepository,
    ): AccountInteractor =
        AccountInteractorImpl(
            repository = repository,
        )

    @Provides
    @Singleton
    fun providesAccountProvider(
        accountInteractor: AccountInteractor,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        exceptionHandler: CoroutineExceptionHandler
    ): AccountPropertiesProvider =
        AccountPropertiesImpl(
            accountInteractor = accountInteractor,
            dispatcher = dispatcher,
            exceptionHandler = exceptionHandler,
        )

    @Provides
    @Singleton
    fun providesCurrencyUpdater() = CurrencyUpdater

    @Provides
    @Singleton
    fun providesBalanceAndNameUpdater() = BalanceAndNameUpdater
}
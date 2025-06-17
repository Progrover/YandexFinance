package dev.progrover.account.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.account.impl.data.api.AccountApi
import dev.progrover.account.impl.data.repository.AccountRepositoryImpl
import dev.progrover.account.api.domain.AccountInteractor
import dev.progrover.account.impl.domain.interactor.AccountInteractorImpl
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideAccountRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        accountApi: AccountApi,
    ): AccountRepository =
        AccountRepositoryImpl(
            accountApi = accountApi,
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
        )

    @Provides
    @Singleton
    fun provideAccountInteractor(
        repository: AccountRepository,
    ): AccountInteractor =
        AccountInteractorImpl(
            repository = repository,
        )
}
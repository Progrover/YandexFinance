package dev.progrover.account.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.account.impl.data.repository.AccountRepositoryImpl
import dev.progrover.account.impl.domain.interactor.AccountInteractor
import dev.progrover.account.impl.domain.interactor.AccountInteractorImpl
import dev.progrover.account.impl.domain.repository.AccountRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideAccountRepository(
    ): AccountRepository =
        AccountRepositoryImpl(
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
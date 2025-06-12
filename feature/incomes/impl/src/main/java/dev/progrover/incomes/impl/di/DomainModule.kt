package dev.progrover.incomes.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.incomes.impl.data.repository.IncomesRepositoryImpl
import dev.progrover.incomes.impl.domain.interactor.IncomesInteractor
import dev.progrover.incomes.impl.domain.interactor.IncomesInteractorImpl
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideIncomesRepository(
    ): IncomesRepository =
        IncomesRepositoryImpl(
        )

    @Provides
    @Singleton
    fun provideIncomesInteractor(
        repository: IncomesRepository,
    ): IncomesInteractor =
        IncomesInteractorImpl(
            repository = repository,
        )
}
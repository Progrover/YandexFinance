package dev.progrover.expenditures.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.expenditures.impl.data.repository.ExpendituresRepositoryImpl
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractor
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractorImpl
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideExpendituresRepository(
    ): ExpendituresRepository =
        ExpendituresRepositoryImpl(
        )

    @Provides
    @Singleton
    fun provideExpendituresInteractor(
        repository: ExpendituresRepository,
    ): ExpendituresInteractor =
        ExpendituresInteractorImpl(
            repository = repository,
        )
}
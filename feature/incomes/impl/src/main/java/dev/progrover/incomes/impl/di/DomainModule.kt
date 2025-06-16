package dev.progrover.incomes.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.incomes.impl.data.repository.IncomesRepositoryImpl
import dev.progrover.incomes.impl.domain.interactor.IncomesInteractor
import dev.progrover.incomes.impl.domain.interactor.IncomesInteractorImpl
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideIncomesRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
    ): IncomesRepository =
        IncomesRepositoryImpl(
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
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
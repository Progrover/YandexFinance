package dev.progrover.history.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.expenditures.api.domain.interactor.ExpendituresInteractor
import dev.progrover.history.impl.data.mapper.HistoryDTOMapper
import dev.progrover.history.impl.data.repository.HistoryRepositoryImpl
import dev.progrover.history.impl.domain.repository.HistoryRepository
import dev.progrover.incomes.api.domain.interactor.IncomesInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        expendituresInteractor: ExpendituresInteractor,
        incomesInteractor: IncomesInteractor,
        historyDTOMapper: HistoryDTOMapper,
    ): HistoryRepository =
        HistoryRepositoryImpl(
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            incomesInteractor = incomesInteractor,
            expendituresInteractor = expendituresInteractor,
            historyDTOMapper = historyDTOMapper,
        )
}
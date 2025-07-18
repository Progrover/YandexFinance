package dev.progrover.expenditures.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.expenditures.api.domain.interactor.ExpendituresInteractor
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.impl.data.repository.ExpendituresRepositoryImpl
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractorImpl
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler

@Module
class DomainModule {

    @Provides
    @ExpendituresScope
    fun provideExpendituresRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        transactionsApi: TransactionsApi,
        expendituresDTOMapper: ExpendituresDTOMapper,
    ): ExpendituresRepository =
        ExpendituresRepositoryImpl(
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            transactionsApi = transactionsApi,
            expendituresDTOMapper = expendituresDTOMapper,
        )

    @Provides
    @ExpendituresScope
    fun provideExpendituresInteractor(
        repository: ExpendituresRepository,
    ): ExpendituresInteractor =
        ExpendituresInteractorImpl(
            repository = repository,
        )
}
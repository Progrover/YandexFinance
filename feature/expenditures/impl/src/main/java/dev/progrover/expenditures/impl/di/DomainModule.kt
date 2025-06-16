package dev.progrover.expenditures.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.expenditures.impl.data.api.ExpendituresApi
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.impl.data.repository.ExpendituresRepositoryImpl
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractor
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractorImpl
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideExpendituresRepository(
        prefs: Prefs,
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        expendituresApi: ExpendituresApi,
        expendituresDTOMapper: ExpendituresDTOMapper,
    ): ExpendituresRepository =
        ExpendituresRepositoryImpl(
            prefs = prefs,
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            expendituresApi = expendituresApi,
            expendituresDTOMapper = expendituresDTOMapper,
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
package dev.progrover.feature.edit.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.feature.edit.impl.data.mapper.EditDTOMapper
import dev.progrover.feature.edit.impl.data.repository.EditRepositoryImpl
import dev.progrover.feature.edit.impl.domain.repository.EditRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler

@Module
class DomainModule {

    @[EditScope Provides]
    fun providesEditRepository(
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
        transactionsApi: TransactionsApi,
        editDTOMapper: EditDTOMapper,
    ): EditRepository =
        EditRepositoryImpl(
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            transactionsApi = transactionsApi,
            editDTOMapper = editDTOMapper,
        )
}
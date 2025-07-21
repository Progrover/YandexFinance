package dev.progrover.feature.edit.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
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
        localTransactionProvider: LocalTransactionProvider,
        localAccountProvider: LocalAccountProvider,
        localArticleProvider: LocalArticleProvider,
    ): EditRepository =
        EditRepositoryImpl(
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
            transactionsApi = transactionsApi,
            editDTOMapper = editDTOMapper,
            localTransactionProvider = localTransactionProvider,
            localAccountProvider = localAccountProvider,
            localArticleProvider = localArticleProvider,
        )
}
package dev.progrover.incomes.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    prefs: Prefs,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
) : IncomesRepository, BaseRepository(
    prefs = prefs,
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
)
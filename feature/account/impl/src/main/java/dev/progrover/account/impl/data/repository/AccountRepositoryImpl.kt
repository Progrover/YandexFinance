package dev.progrover.account.impl.data.repository

import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    prefs: Prefs,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
) : AccountRepository, BaseRepository(
    prefs = prefs,
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
)
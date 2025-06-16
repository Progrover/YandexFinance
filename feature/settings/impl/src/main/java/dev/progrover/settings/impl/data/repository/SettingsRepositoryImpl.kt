package dev.progrover.settings.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.settings.impl.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    prefs: Prefs,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
) : SettingsRepository, BaseRepository(
    prefs = prefs,
    dispatcher = dispatcher,
    coroutineExceptionHandler = coroutineExceptionHandler,
)
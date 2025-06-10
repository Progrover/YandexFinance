package dev.progrover.settings.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.settings.impl.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
) : SettingsRepository, BaseRepository(Dispatchers.IO)
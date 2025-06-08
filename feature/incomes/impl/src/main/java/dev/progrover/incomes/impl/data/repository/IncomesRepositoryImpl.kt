package dev.progrover.incomes.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
) : IncomesRepository, BaseRepository(Dispatchers.IO)
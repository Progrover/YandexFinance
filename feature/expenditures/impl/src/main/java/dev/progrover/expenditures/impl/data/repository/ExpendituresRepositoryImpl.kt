package dev.progrover.expenditures.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExpendituresRepositoryImpl @Inject constructor(
) : ExpendituresRepository, BaseRepository(Dispatchers.IO)
package dev.progrover.account.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.account.impl.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
) : AccountRepository, BaseRepository(Dispatchers.IO)
package dev.progrover.account.impl.data.repository

import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.data.repository.BaseRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
) : AccountRepository, BaseRepository()
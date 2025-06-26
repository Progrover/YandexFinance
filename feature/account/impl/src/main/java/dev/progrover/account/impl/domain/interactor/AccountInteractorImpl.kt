package dev.progrover.account.impl.domain.interactor

import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse
import javax.inject.Inject

class AccountInteractorImpl @Inject constructor(
    private val repository: AccountRepository,
) : AccountInteractor {
    override suspend fun getAccounts(): ApiResponse<List<AccountDetailed>> =
        repository.getAccounts()
}
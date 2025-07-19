package dev.progrover.account.impl.domain.interactor

import dev.progrover.account.api.domain.interactor.AccountInteractor
import dev.progrover.account.impl.domain.repository.AccountRepository
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse
import javax.inject.Inject

class AccountInteractorImpl @Inject constructor(
    private val repository: AccountRepository,
) : AccountInteractor {
    override suspend fun getAccounts(): ApiResponse<List<AccountDetailed>> =
        repository.getAccounts()

    override suspend fun getAccountById(id: Int): ApiResponse<AccountDetailed> =
        repository.getAccountById(id)

    override suspend fun getAccountsFromLocalStorage(): ApiResponse<List<AccountDetailed>> =
        repository.getAccountsFromLocalStorage()

    override suspend fun getAccountByIdFromLocalStorage(id: Int): ApiResponse<AccountDetailed> =
        repository.getAccountByIdFromLocalStorage(id)

    override suspend fun updateAccount(account: AccountDetailed) {
        repository.updateAccountById(account)
    }
}
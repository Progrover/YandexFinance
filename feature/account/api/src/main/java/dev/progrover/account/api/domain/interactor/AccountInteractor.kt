package dev.progrover.account.api.domain.interactor

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse

interface AccountInteractor {
    suspend fun getAccounts(): ApiResponse<List<AccountDetailed>>

    suspend fun getAccountById(id: Int): ApiResponse<AccountDetailed>

    suspend fun getAccountsFromLocalStorage(): ApiResponse<List<AccountDetailed>>

    suspend fun getAccountByIdFromLocalStorage(id: Int): ApiResponse<AccountDetailed>
}
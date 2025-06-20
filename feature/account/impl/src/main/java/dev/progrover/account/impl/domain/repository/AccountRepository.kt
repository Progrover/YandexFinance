package dev.progrover.account.impl.domain.repository

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse

interface AccountRepository {
    suspend fun getAccounts(): ApiResponse<List<AccountDetailed>>

    suspend fun getAccountById(accountId: Int): ApiResponse<AccountDetailed>

    suspend fun createAccount(
        name: String,
        balance: String,
        currency: String,
    ): ApiResponse<AccountDetailed>

    suspend fun updateAccountById(
        account: AccountDetailed,
    ): ApiResponse<AccountDetailed>

    suspend fun deleteAccountById(accountId: Int): ApiResponse<Boolean>
}
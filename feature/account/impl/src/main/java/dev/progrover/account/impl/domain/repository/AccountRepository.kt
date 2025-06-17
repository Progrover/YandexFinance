package dev.progrover.account.impl.domain.repository

import dev.progrover.core.base.model.AccountDetailed

interface AccountRepository {
    suspend fun getAccounts(): Result<List<AccountDetailed>>

    suspend fun getAccountById(accountId: Int): Result<AccountDetailed>

    suspend fun createAccount(
        name: String,
        balance: String,
        currency: String,
    ): Result<AccountDetailed>

    suspend fun updateAccountById(
        account: AccountDetailed,
    ): Result<AccountDetailed>

    suspend fun deleteAccountById(accountId: Int): String?
}
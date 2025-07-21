package dev.progrover.core.base.data.local.provider

import dev.progrover.core.base.model.AccountDetailed
/**
 * Класс для работы с локальной бд
 */
interface LocalAccountProvider {
    suspend fun getAllAccounts(): List<AccountDetailed>

    suspend fun getAccountById(accountId: Int): AccountDetailed?

    suspend fun updateAccount(account: AccountDetailed, synced: Boolean): Boolean
}
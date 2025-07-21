package dev.progrover.core.base.data.local.provider

import dev.progrover.core.base.model.Transaction
/**
 * Класс для работы с локальной бд
 */
interface LocalTransactionProvider {

    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<Transaction>

    suspend fun createTransaction(transaction: Transaction, synced: Boolean): Boolean

    suspend fun getTransactionById(transactionId: Int): Transaction?

    suspend fun updateTransaction(transaction: Transaction, synced: Boolean): Boolean

    suspend fun deleteTransactionById(transactionId: Int): Boolean

    suspend fun clearTransactions()
}
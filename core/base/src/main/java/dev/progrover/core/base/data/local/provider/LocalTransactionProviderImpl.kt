package dev.progrover.core.base.data.local.provider

import dev.progrover.core.base.data.local.AccountDao
import dev.progrover.core.base.data.local.ArticleDao
import dev.progrover.core.base.data.local.TransactionDao
import dev.progrover.core.base.data.local.mapper.AccountEntityMapper
import dev.progrover.core.base.data.local.mapper.CategoryEntityMapper
import dev.progrover.core.base.data.local.mapper.TransactionEntityMapper
import dev.progrover.core.base.model.Transaction
import timber.log.Timber
import javax.inject.Inject

internal class LocalTransactionProviderImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val articleDao: ArticleDao,
    private val accountDao: AccountDao,
    private val transactionEntityMapper: TransactionEntityMapper,
    private val accountEntityMapper: AccountEntityMapper,
    private val categoryEntityMapper: CategoryEntityMapper,
) : LocalTransactionProvider {
    override suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<Transaction> =
        try {
            transactionDao.getTransactionsByAccountAndPeriod(
                accountId,
                startDate,
                endDate
            ).map { transactionEntity ->
                transactionEntityMapper.toTransaction(
                    transactionEntity = transactionEntity,
                    getCategory = {
                        categoryEntityMapper.toCategory(
                            articleDao.getCategoryById(
                                transactionEntity.categoryId
                            )
                        )
                    },
                    getAccount = {
                        accountEntityMapper.toAccount(
                            accountDao.getAccountById(
                                transactionEntity.accountId
                            )
                        )
                    },
                )
            }
        } catch (e: Exception) {
            Timber.e("GetTransactions locally error: ${e.message}")
            emptyList()
        }

    override suspend fun createTransaction(transaction: Transaction, synced: Boolean): Boolean =
        try {
            transactionDao.createTransaction(
                transactionEntityMapper.toTransactionEntity(transaction).copy(synced = synced)
            )
            true
        } catch (e: Exception) {
            Timber.e("CreateTransaction locally error: ${e.message}")
            false
        }

    override suspend fun getTransactionById(transactionId: Int): Transaction? =
        try {
            transactionDao.getTransactionById(transactionId).let { transactionEntity ->
                transactionEntityMapper.toTransaction(
                    transactionEntity,
                    getCategory = {
                        categoryEntityMapper.toCategory(
                            articleDao.getCategoryById(
                                transactionEntity.categoryId
                            )
                        )
                    },
                    getAccount = {
                        accountEntityMapper.toAccount(
                            accountDao.getAccountById(
                                transactionEntity.accountId
                            )
                        )
                    },
                )
            }
        } catch (e: Exception) {
            Timber.e("GetTransactionById locally error: ${e.message}")
            null
        }

    override suspend fun updateTransaction(transaction: Transaction, synced: Boolean): Boolean =
        try {
            transactionDao.updateTransaction(
                transactionEntityMapper.toTransactionEntity(transaction).copy(synced = synced)
            )
            true
        } catch (e: Exception) {
            Timber.e("UpdateTransaction locally error: ${e.message}")
            false
        }

    override suspend fun deleteTransactionById(transactionId: Int): Boolean =
        try {
            transactionDao.deleteTransactionById(
                transactionId
            )
            true
        } catch (e: Exception) {
            Timber.e("DeleteTransaction locally error: ${e.message}")
            false
        }

    override suspend fun clearTransactions() {
        transactionDao.clearTransactions()
    }
}
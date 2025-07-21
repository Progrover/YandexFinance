package dev.progrover.core.base.data.local.mapper

import dev.progrover.core.base.data.local.entity.TransactionEntity
import dev.progrover.core.base.model.Account
import dev.progrover.core.base.model.Category
import dev.progrover.core.base.model.Transaction
import dev.progrover.core.base.utils.formatToIsoUtc
import dev.progrover.core.base.utils.toMillis

interface TransactionEntityMapper {
    suspend fun toTransactionEntity(transaction: Transaction): TransactionEntity
    suspend fun toTransaction(
        transactionEntity: TransactionEntity,
        getCategory: suspend (Int) -> Category,
        getAccount: suspend (Int) -> Account,
    ): Transaction
}

internal class TransactionEntityMapperImpl : TransactionEntityMapper {
    override suspend fun toTransactionEntity(transaction: Transaction): TransactionEntity =
        TransactionEntity(
            id = transaction.id,
            accountId = transaction.account.id,
            categoryId = transaction.category.id,
            amount = transaction.amount,
            transactionDate = transaction.transactionDate.toMillis(),
            comment = transaction.comment,
            createdAt = transaction.createdAt.toMillis(),
            updatedAt = transaction.updatedAt.toMillis(),
        )

    override suspend fun toTransaction(
        transactionEntity: TransactionEntity,
        getCategory: suspend (Int) -> Category,
        getAccount: suspend (Int) -> Account,
    ): Transaction =
        Transaction(
            id = transactionEntity.id,
            account = getAccount(transactionEntity.accountId),
            category = getCategory(transactionEntity.categoryId),
            amount = transactionEntity.amount,
            transactionDate = transactionEntity.transactionDate.formatToIsoUtc(),
            comment = transactionEntity.comment,
            createdAt = transactionEntity.createdAt.formatToIsoUtc(),
            updatedAt = transactionEntity.updatedAt.formatToIsoUtc(),
            synced = transactionEntity.synced,
        )
}
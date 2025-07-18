package dev.progrover.core.base.data.local.mapper

import dev.progrover.core.base.data.local.entity.AccountEntity
import dev.progrover.core.base.model.Account
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.utils.formatToIsoUtc
import dev.progrover.core.base.utils.toMillis

interface AccountEntityMapper {
    suspend fun toAccountEntity(accountDetailed: AccountDetailed): AccountEntity
    suspend fun toAccountDetailed(accountEntity: AccountEntity): AccountDetailed
    suspend fun toAccount(accountEntity: AccountEntity): Account
}

internal class AccountEntityMapperImpl : AccountEntityMapper {
    override suspend fun toAccountEntity(accountDetailed: AccountDetailed): AccountEntity =
        AccountEntity(
            id = accountDetailed.id,
            userId = accountDetailed.userId,
            name = accountDetailed.name,
            balance = accountDetailed.balance,
            currency = accountDetailed.currency,
            createdAt = accountDetailed.createdAt.toMillis(),
            updatedAt = accountDetailed.updatedAt.toMillis(),
        )

    override suspend fun toAccountDetailed(accountEntity: AccountEntity): AccountDetailed =
        AccountDetailed(
            id = accountEntity.id,
            userId = accountEntity.userId,
            name = accountEntity.name,
            balance = accountEntity.balance,
            currency = accountEntity.currency,
            createdAt = accountEntity.createdAt.formatToIsoUtc(),
            updatedAt = accountEntity.createdAt.formatToIsoUtc(),
        )

    override suspend fun toAccount(accountEntity: AccountEntity): Account =
        Account(
            id = accountEntity.id,
            name = accountEntity.name,
            balance = accountEntity.balance,
            currency = accountEntity.currency,
        )
}
package dev.progrover.core.base.data.local.provider

import dev.progrover.core.base.data.local.AccountDao
import dev.progrover.core.base.data.local.mapper.AccountEntityMapper
import dev.progrover.core.base.model.AccountDetailed
import timber.log.Timber
import javax.inject.Inject

internal class LocalAccountProviderImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val accountEntityMapper: AccountEntityMapper,
) : LocalAccountProvider {
    override suspend fun getAllAccounts(): List<AccountDetailed> =
        try {
            accountDao.getAllAccounts().map { accountEntityMapper.toAccountDetailed(it) }
        } catch (e: Exception) {
            Timber.e("GetAccounts error: ${e.message}")
            emptyList()
        }

    override suspend fun getAccountById(accountId: Int): AccountDetailed? =
        try {
            accountEntityMapper.toAccountDetailed(accountDao.getAccountById(accountId))
        } catch (e: Exception) {
            Timber.e("GetAccountById error: ${e.message}")
            null
        }

    override suspend fun updateAccount(account: AccountDetailed, synced: Boolean): Boolean =
        try {
            accountDao.updateAccount(
                accountEntityMapper.toAccountEntity(account).copy(synced = synced)
            )
            true
        } catch (e: Exception) {
            Timber.e("UpdateAccount error: ${e.message}")
            false
        }
}
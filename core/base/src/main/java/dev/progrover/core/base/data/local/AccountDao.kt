package dev.progrover.core.base.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.progrover.core.base.data.local.entity.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE id = :accountId LIMIT 1")
    suspend fun getAccountById(accountId: Int): AccountEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAccount(account: AccountEntity)
}

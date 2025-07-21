package dev.progrover.core.base.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.progrover.core.base.data.local.entity.TransactionEntity

@Dao
interface TransactionDao {
    @Query(
        """
        SELECT * FROM transactions 
        WHERE accountId = :accountId 
          AND transactionDate BETWEEN :startDate AND :endDate
    """
    )
    suspend fun getTransactionsByAccountAndPeriod(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE id = :transactionId LIMIT 1")
    suspend fun getTransactionById(transactionId: Int): TransactionEntity

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: Int)
}
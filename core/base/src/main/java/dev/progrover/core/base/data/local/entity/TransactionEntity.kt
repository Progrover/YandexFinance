package dev.progrover.core.base.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: Long,
    val comment: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val synced: Boolean = false
)
package dev.progrover.core.base.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.progrover.core.base.data.local.entity.AccountEntity
import dev.progrover.core.base.data.local.entity.CategoryEntity
import dev.progrover.core.base.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        AccountEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): ArticleDao
    abstract fun accountDao(): AccountDao
}
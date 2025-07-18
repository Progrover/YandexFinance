package dev.progrover.core.base.data.local

import androidx.room.Dao
import androidx.room.Query
import dev.progrover.core.base.data.local.entity.CategoryEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE isIncome = :isIncome ORDER BY name ASC")
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: Int): CategoryEntity
}

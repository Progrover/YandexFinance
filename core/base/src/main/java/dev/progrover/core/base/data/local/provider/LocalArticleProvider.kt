package dev.progrover.core.base.data.local.provider

import dev.progrover.core.base.model.Category

/**
 * Класс для работы с локальной бд
 */
interface LocalArticleProvider {
    suspend fun getAllCategories(): List<Category>

    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>

    suspend fun getCategoryById(categoryId: Int): Category?

    suspend fun createCategory(category: Category)
}
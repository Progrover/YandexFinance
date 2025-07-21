package dev.progrover.core.base.data.local.provider

import dev.progrover.core.base.data.local.ArticleDao
import dev.progrover.core.base.data.local.mapper.CategoryEntityMapper
import dev.progrover.core.base.model.Category
import timber.log.Timber
import javax.inject.Inject

internal class LocalArticleProviderImpl @Inject constructor(
    private val categoryDao: ArticleDao,
    private val categoryEntityMapper: CategoryEntityMapper,
) : LocalArticleProvider {
    override suspend fun getAllCategories(): List<Category> =
        try {
            categoryDao.getAllCategories().map { categoryEntityMapper.toCategory(it) }
        } catch (e: Exception) {
            Timber.e("GetCategories error: ${e.message}")
            emptyList()
        }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> =
        try {
            categoryDao.getCategoriesByType(isIncome).map { categoryEntityMapper.toCategory(it) }
        } catch (e: Exception) {
            Timber.e("GetCategoriesByType error: ${e.message}")
            emptyList()
        }

    override suspend fun getCategoryById(categoryId: Int): Category? =
        try {
            categoryEntityMapper.toCategory(categoryDao.getCategoryById(categoryId))
        } catch (e: Exception) {
            Timber.e("GetCategoryById error: ${e.message}")
            null
        }

    override suspend fun createCategory(category: Category) {
        try {
            categoryDao.createCategory(categoryEntityMapper.toCategoryEntity(category))
        } catch (e: Exception) {
            Timber.e("CreateCategory error: ${e.message}")
        }
    }
}
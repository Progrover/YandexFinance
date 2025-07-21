package dev.progrover.core.base.data.local.mapper

import dev.progrover.core.base.data.local.entity.CategoryEntity
import dev.progrover.core.base.model.Category

interface CategoryEntityMapper {
    suspend fun toCategoryEntity(category: Category): CategoryEntity
    suspend fun toCategory(categoryEntity: CategoryEntity): Category
}

internal class CategoryEntityMapperImpl : CategoryEntityMapper {
    override suspend fun toCategoryEntity(category: Category): CategoryEntity =
        CategoryEntity(
            id = category.id,
            name = category.name,
            emoji = category.emoji,
            isIncome = category.isIncome
        )

    override suspend fun toCategory(categoryEntity: CategoryEntity): Category =
        Category(
            id = categoryEntity.id,
            name = categoryEntity.name,
            emoji = categoryEntity.emoji,
            isIncome = categoryEntity.isIncome
        )
}
package dev.progrover.articles.impl.presentation.contract.articles

import dev.progrover.core.base.model.Category
import dev.progrover.core.base.presentation.mvi.UIState

data class ArticlesUIState(
    val isLoading: Boolean = false,
    val articles: List<Category> =
        listOf(
            Category(
                id = 1,
                emoji = "\uD83C\uDFE1",
                name = "Аренда квартиры ",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "\uD83D\uDC57",
                name = "Одежда",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "\uD83D\uDC36",
                name = "На собачку",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "\uD83D\uDC36",
                name = "На собачку",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "РК",
                name = "Ремонт квартиры",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "\uD83C\uDF6D",
                name = "Продукты",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "\uD83C\uDFCB\uD83C\uDFFF",
                name = "Спортзал",
                isIncome = true,
            ),
            Category(
                id = 1,
                emoji = "\uD83D\uDC8A",
                name = "Медицина",
                isIncome = true,
            ),
        )
) : UIState
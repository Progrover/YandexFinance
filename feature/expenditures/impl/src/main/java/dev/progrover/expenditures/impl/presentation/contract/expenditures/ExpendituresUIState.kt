package dev.progrover.expenditures.impl.presentation.contract.expenditures

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.expenditures.impl.domain.model.Expenditure

data class ExpendituresUIState(
    val isLoading: Boolean = false,
    val totalExpenditures: String = "436 558 ₽",
    val expenditures: List<Expenditure> =
        listOf(
            Expenditure(
                id = 1,
                emoji = "\uD83C\uDFE1",
                name = "Аренда квартиры ",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "\uD83D\uDC57",
                name = "Одежда",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "\uD83D\uDC36",
                name = "На собачку",
                comment = "Джек",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "\uD83D\uDC36",
                name = "На собачку",
                comment = "Энни",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "РК",
                name = "Ремонт квартиры",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "\uD83C\uDF6D",
                name = "Продукты",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "\uD83C\uDFCB\uD83C\uDFFF",
                name = "Спортзал",
                amount = "100 000 ₽",
            ),
            Expenditure(
                id = 1,
                emoji = "\uD83D\uDC8A",
                name = "Медицина",
                amount = "100 000 ₽",
            ),
        )
) : UIState
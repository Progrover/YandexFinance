package dev.progrover.history.impl.data.mapper

import dev.progrover.expenditures.api.domain.model.Expenditure
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.incomes.api.domain.model.Income
import timber.log.Timber

class HistoryDTOMapperImpl : HistoryDTOMapper {

    override fun mapExpendituresToHistory(expenditures: List<Expenditure>): List<HistoryElement> =
        expenditures.mapNotNull { mapExpendToHistory(it) }

    override fun mapIncomesToHistory(incomes: List<Income>): List<HistoryElement> =
        incomes.mapNotNull { mapIncomeToHistory(it) }

    private fun mapExpendToHistory(element: Expenditure): HistoryElement? =
        try {
            HistoryElement(
                id = element.id,
                name = element.name,
                emoji = element.emoji,
                comment = element.comment,
                amount = element.amount,
            )
        } catch (e: Exception) {
            Timber.e("Expenditure to history error", e)
            null
        }

    private fun mapIncomeToHistory(element: Income): HistoryElement? =
        try {
            HistoryElement(
                id = element.id,
                name = element.name,
                emoji = element.emoji,
                comment = element.comment,
                amount = element.amount,
            )
        } catch (e: Exception) {
            Timber.e("Income to history error", e)
            null
        }
}
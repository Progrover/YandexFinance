package dev.progrover.history.impl.data.mapper

import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.incomes.api.domain.model.IncomeDetailed
import timber.log.Timber

class HistoryDTOMapperImpl : HistoryDTOMapper {

    override fun mapExpendituresToHistory(expenditures: List<ExpenditureDetailed>): List<HistoryElement> =
        expenditures.mapNotNull { mapExpendToHistory(it) }

    override fun mapIncomesToHistory(incomes: List<IncomeDetailed>): List<HistoryElement> =
        incomes.mapNotNull { mapIncomeToHistory(it) }

    private fun mapExpendToHistory(element: ExpenditureDetailed): HistoryElement? =
        try {
            HistoryElement(
                id = element.id,
                name = element.name,
                emoji = element.emoji,
                comment = element.comment,
                amount = element.amount,
                dateTime = element.dateTime
            )
        } catch (e: Exception) {
            Timber.e("Expenditure to history error", e)
            null
        }

    private fun mapIncomeToHistory(element: IncomeDetailed): HistoryElement? =
        try {
            HistoryElement(
                id = element.id,
                name = element.name,
                emoji = element.emoji,
                comment = element.comment,
                amount = element.amount,
                dateTime = element.dateTime
            )
        } catch (e: Exception) {
            Timber.e("Income to history error", e)
            null
        }
}
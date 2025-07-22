package dev.progrover.history.impl.data.mapper

import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.history.impl.domain.model.AnalysisElement
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.incomes.api.domain.model.IncomeDetailed
import timber.log.Timber

class HistoryDTOMapperImpl : HistoryDTOMapper {

    override fun mapExpendituresToHistory(expenditures: List<ExpenditureDetailed>): List<HistoryElement> =
        expenditures.mapNotNull { mapExpendToHistory(it) }

    override fun mapIncomesToHistory(incomes: List<IncomeDetailed>): List<HistoryElement> =
        incomes.mapNotNull { mapIncomeToHistory(it) }

    override fun mapExpendituresToAnalysis(expenditures: List<ExpenditureDetailed>): List<AnalysisElement> =
        expenditures.mapNotNull { mapExpendToAnalysis(it) }

    override fun mapIncomesToAnalysis(incomes: List<IncomeDetailed>): List<AnalysisElement> =
        incomes.mapNotNull { mapIncomeToAnalysis(it) }

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

    private fun mapExpendToAnalysis(element: ExpenditureDetailed): AnalysisElement? =
        try {
            AnalysisElement(
                id = element.id,
                name = element.name,
                emoji = element.emoji,
                comment = element.comment,
                amount = element.amount,
                dateTime = element.dateTime
            )
        } catch (e: Exception) {
            Timber.e("Expenditure to analysis error", e)
            null
        }

    private fun mapIncomeToAnalysis(element: IncomeDetailed): AnalysisElement? =
        try {
            AnalysisElement(
                id = element.id,
                name = element.name,
                emoji = element.emoji,
                comment = element.comment,
                amount = element.amount,
                dateTime = element.dateTime
            )
        } catch (e: Exception) {
            Timber.e("Income to analysis error", e)
            null
        }
}
package dev.progrover.history.impl.data.mapper

import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.incomes.api.domain.model.IncomeDetailed

interface HistoryDTOMapper {
    fun mapExpendituresToHistory(
        expenditures: List<ExpenditureDetailed>
    ): List<HistoryElement>

    fun mapIncomesToHistory(
        incomes: List<IncomeDetailed>
    ): List<HistoryElement>
}
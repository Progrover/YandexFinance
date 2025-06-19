package dev.progrover.history.impl.data.mapper

import dev.progrover.expenditures.api.domain.model.Expenditure
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.incomes.api.domain.model.Income

interface HistoryDTOMapper {
    fun mapExpendituresToHistory(
        expenditures: List<Expenditure>
    ): List<HistoryElement>

    fun mapIncomesToHistory(
        incomes: List<Income>
    ): List<HistoryElement>
}
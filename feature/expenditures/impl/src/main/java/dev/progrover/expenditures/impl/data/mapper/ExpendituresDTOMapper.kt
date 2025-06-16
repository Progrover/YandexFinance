package dev.progrover.expenditures.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresDTOMapper {
    fun mapTransactionsToExpenditures(
        transactions: List<Transaction>
    ) : List<Expenditure>
}
package dev.progrover.expenditures.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresDTOMapper {
    fun mapTransactionsToExpenditures(
        transactions: List<Transaction>
    ): List<Expenditure>

    fun mapTransactionsToExpendituresDetailed(
        transactions: List<Transaction>
    ): List<ExpenditureDetailed>
}
package dev.progrover.expenditures.impl.domain.repository

import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresRepository {
    suspend fun getExpenditures(accountId: Int): Result<Pair<String, List<Expenditure>>>
}
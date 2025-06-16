package dev.progrover.expenditures.impl.domain.interactor

import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresInteractor {
    suspend fun getExpenditures(accountId: Int): Result<Pair<String, List<Expenditure>>>
}
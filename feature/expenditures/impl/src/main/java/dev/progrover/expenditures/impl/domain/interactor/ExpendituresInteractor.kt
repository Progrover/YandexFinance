package dev.progrover.expenditures.impl.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresInteractor {
    suspend fun getExpenditures(accountId: Int): ApiResponse<Pair<String, List<Expenditure>>>
}
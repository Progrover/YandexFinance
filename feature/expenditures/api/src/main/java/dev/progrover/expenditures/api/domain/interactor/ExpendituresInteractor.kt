package dev.progrover.expenditures.api.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.api.domain.model.Expenditure

interface ExpendituresInteractor {
    suspend fun getExpenditures(accountId: Int, startDate: String? = null, endDate: String? = null): ApiResponse<Pair<String, List<Expenditure>>>
}
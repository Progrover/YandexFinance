package dev.progrover.expenditures.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.api.domain.model.Expenditure

interface ExpendituresRepository {
    suspend fun getExpenditures(
        accountId: Int,
        start: String?,
        end: String?
    ): ApiResponse<Pair<String, List<Expenditure>>>
}
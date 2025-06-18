package dev.progrover.expenditures.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresRepository {
    suspend fun getExpenditures(accountId: Int): ApiResponse<Pair<String, List<Expenditure>>>
}
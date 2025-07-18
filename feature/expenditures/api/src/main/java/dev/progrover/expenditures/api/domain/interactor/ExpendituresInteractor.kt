package dev.progrover.expenditures.api.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed

interface ExpendituresInteractor {
    suspend fun getExpendituresDetailed(
        accountId: Int,
        startDate: String,
        endDate: String
    ): ApiResponse<List<ExpenditureDetailed>>

    suspend fun getExpendituresDetailedFromLocalStorage(
        accountId: Int,
        startDate: String,
        endDate: String
    ): ApiResponse<List<ExpenditureDetailed>>
}
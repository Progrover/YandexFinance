package dev.progrover.incomes.api.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.api.domain.model.IncomeDetailed

interface IncomesInteractor {
    suspend fun getIncomesDetailed(
        accountId: Int,
        start: String,
        end: String,
    ): ApiResponse<List<IncomeDetailed>>

    suspend fun getIncomesDetailedFromLocalStorage(
        accountId: Int,
        start: String,
        end: String,
    ): ApiResponse<List<IncomeDetailed>>
}
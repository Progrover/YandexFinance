package dev.progrover.incomes.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.api.domain.model.IncomeDetailed
import dev.progrover.incomes.impl.domain.model.Income

interface IncomesRepository {
    suspend fun getIncomes(
        accountId: Int,
    ): ApiResponse<List<Income>>

    suspend fun getIncomesDetailed(
        accountId: Int,
        start: String,
        end: String,
    ): ApiResponse<List<IncomeDetailed>>
}
package dev.progrover.incomes.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.api.domain.model.Income

interface IncomesRepository {
    suspend fun getIncomes(
        accountId: Int,
        start: String?,
        end: String?
    ): ApiResponse<Pair<String, List<Income>>>
}
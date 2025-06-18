package dev.progrover.incomes.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.impl.domain.model.Income

interface IncomesRepository {
    suspend fun getIncomes(accountId: Int): ApiResponse<Pair<String, List<Income>>>
}
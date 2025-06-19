package dev.progrover.incomes.api.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.api.domain.model.Income

interface IncomesInteractor {
    suspend fun getIncomes(
        accountId: Int,
        start: String? = null,
        end: String? = null,
    ): ApiResponse<Pair<String, List<Income>>>
}
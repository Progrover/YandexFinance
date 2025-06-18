package dev.progrover.incomes.impl.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.impl.domain.model.Income

interface IncomesInteractor {
    suspend fun getIncomes(accountId: Int): ApiResponse<Pair<String, List<Income>>>
}
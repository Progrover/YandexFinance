package dev.progrover.incomes.impl.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.api.domain.interactor.IncomesInteractor
import dev.progrover.incomes.api.domain.model.IncomeDetailed
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class IncomesInteractorImpl @Inject constructor(
    private val repository: IncomesRepository,
) : IncomesInteractor {

    override suspend fun getIncomesDetailed(
        accountId: Int,
        start: String,
        end: String
    ): ApiResponse<Pair<String, List<IncomeDetailed>>> =
        repository.getIncomesDetailed(accountId, start, end)
}
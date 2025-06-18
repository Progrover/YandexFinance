package dev.progrover.incomes.impl.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.incomes.impl.domain.model.Income
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class IncomesInteractorImpl @Inject constructor(
    private val repository: IncomesRepository,
) : IncomesInteractor {
    override suspend fun getIncomes(accountId: Int): ApiResponse<Pair<String, List<Income>>> =
        repository.getIncomes(accountId)
}
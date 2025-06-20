package dev.progrover.expenditures.impl.domain.interactor

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.api.domain.interactor.ExpendituresInteractor
import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import javax.inject.Inject

class ExpendituresInteractorImpl @Inject constructor(
    private val repository: ExpendituresRepository,
) : ExpendituresInteractor {
    override suspend fun getExpendituresDetailed(
        accountId: Int,
        startDate: String,
        endDate: String
    ): ApiResponse<Pair<String, List<ExpenditureDetailed>>> =
        repository.getExpendituresDetailed(accountId, startDate, endDate)
}
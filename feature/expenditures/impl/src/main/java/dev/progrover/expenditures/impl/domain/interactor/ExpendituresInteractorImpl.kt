package dev.progrover.expenditures.impl.domain.interactor

import dev.progrover.expenditures.impl.domain.model.Expenditure
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import javax.inject.Inject

class ExpendituresInteractorImpl @Inject constructor(
    private val repository: ExpendituresRepository,
) : ExpendituresInteractor {
    override suspend fun getExpenditures(accountId: Int): Result<Pair<String, List<Expenditure>>> =
        repository.getExpenditures(accountId)
}
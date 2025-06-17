package dev.progrover.incomes.impl.domain.interactor

import dev.progrover.incomes.impl.domain.model.Income

interface IncomesInteractor {
    suspend fun getIncomes(accountId: Int): Result<Pair<String, List<Income>>>
}
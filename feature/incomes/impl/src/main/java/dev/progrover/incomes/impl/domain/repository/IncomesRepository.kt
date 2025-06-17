package dev.progrover.incomes.impl.domain.repository

import dev.progrover.incomes.impl.domain.model.Income

interface IncomesRepository {
    suspend fun getIncomes(accountId: Int): Result<Pair<String, List<Income>>>
}
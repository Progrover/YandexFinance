package dev.progrover.incomes.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.incomes.impl.domain.model.Income

interface IncomesDTOMapper {
    fun mapTransactionsToIncomes(transactions: List<Transaction>): List<Income>
}
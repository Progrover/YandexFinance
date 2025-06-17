package dev.progrover.incomes.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.incomes.impl.domain.model.Income
import timber.log.Timber

class IncomesDTOMapperImpl : IncomesDTOMapper {
    override fun mapTransactionsToIncomes(transactions: List<Transaction>): List<Income> =
        transactions.mapNotNull { transaction -> mapTransactionToIncome(transaction) }

    private fun mapTransactionToIncome(transaction: Transaction): Income? =
        try {
            Income(
                id = transaction.id,
                name = transaction.category.name,
                amount = transaction.amount,
                comment = transaction.comment,
                emoji = transaction.category.emoji,
            )
        } catch (e: Exception) {
            Timber.e("Transaction to income error", e)
            null
        }
}
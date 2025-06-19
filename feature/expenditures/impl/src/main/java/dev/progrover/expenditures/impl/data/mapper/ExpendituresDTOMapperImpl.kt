package dev.progrover.expenditures.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.expenditures.api.domain.model.Expenditure
import timber.log.Timber

class ExpendituresDTOMapperImpl : ExpendituresDTOMapper {
    override fun mapTransactionsToExpenditures(transactions: List<Transaction>) =
        transactions.mapNotNull { transaction -> mapTransactionToExpenditure(transaction) }

    private fun mapTransactionToExpenditure(transaction: Transaction): Expenditure? =
        try {
            Expenditure(
                id = transaction.id,
                amount = transaction.amount,
                name = transaction.category.name,
                comment = transaction.comment,
                emoji = transaction.category.emoji,
            )
        } catch (e: Exception) {
            Timber.e("Transaction to expenditure error", e)
            null
        }
}
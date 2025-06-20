package dev.progrover.expenditures.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.core.base.utils.toMillis
import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.expenditures.impl.domain.model.Expenditure
import timber.log.Timber

class ExpendituresDTOMapperImpl : ExpendituresDTOMapper {
    override fun mapTransactionsToExpenditures(transactions: List<Transaction>) =
        transactions.mapNotNull { transaction -> mapTransactionToExpenditure(transaction) }

    override fun mapTransactionsToExpendituresDetailed(transactions: List<Transaction>): List<ExpenditureDetailed> =
        transactions.mapNotNull { transaction -> mapTransactionToExpenditureDetailed(transaction) }

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

    private fun mapTransactionToExpenditureDetailed(transaction: Transaction): ExpenditureDetailed? =
        try {
            ExpenditureDetailed(
                id = transaction.id,
                amount = transaction.amount,
                name = transaction.category.name,
                comment = transaction.comment,
                emoji = transaction.category.emoji,
                dateTime = transaction.transactionDate.toMillis(),
            )
        } catch (e: Exception) {
            Timber.e("Transaction to detailed expenditure error", e)
            null
        }
}
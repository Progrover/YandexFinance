package dev.progrover.feature.edit.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.core.base.utils.toMillis
import dev.progrover.feature.edit.impl.domain.model.EditTransaction
import timber.log.Timber

class EditDTOMapperImpl : EditDTOMapper {
    override fun mapTransactionToEditTransaction(transaction: Transaction): EditTransaction? =
        try {
            EditTransaction(
                id = transaction.id,
                accountName = transaction.account.name,
                categoryId = transaction.category.id,
                comment = transaction.comment,
                amount = transaction.amount,
                accountId = transaction.account.id,
                dateTime = transaction.transactionDate.toMillis(),
            )
        } catch (e: Exception) {
            Timber.e("Transaction to editTransaction error", e)
            null
        }
}
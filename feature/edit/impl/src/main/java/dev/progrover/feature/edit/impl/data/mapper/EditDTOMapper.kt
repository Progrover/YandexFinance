package dev.progrover.feature.edit.impl.data.mapper

import dev.progrover.core.base.model.Transaction
import dev.progrover.feature.edit.impl.domain.model.EditTransaction

interface EditDTOMapper {
    fun mapTransactionToEditTransaction(
        transaction: Transaction
    ): EditTransaction?
}
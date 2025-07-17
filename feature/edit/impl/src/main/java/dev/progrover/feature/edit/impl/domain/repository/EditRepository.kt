package dev.progrover.feature.edit.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.feature.edit.impl.domain.model.EditTransaction

interface EditRepository {

    suspend fun getEditTransaction(
        accountId: Int,
        transactionId: Int,
    ): ApiResponse<EditTransaction>

    suspend fun updateTransactionInfo(
        transaction: EditTransaction,
    ): ApiResponse<Boolean>

    suspend fun addTransaction(
        transaction: EditTransaction
    ): ApiResponse<Boolean>

    suspend fun deleteTransaction(
        transactionId: Int
    ): ApiResponse<Boolean>
}
package dev.progrover.core.base.data.api

import dev.progrover.core.base.model.Transaction
import dev.progrover.core.base.model.request.CreateTransactionRequest
import dev.progrover.core.base.model.request.UpdateTransactionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Необходим для получения информации с сервера о транзакциях
 */
interface TransactionsApi {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): Response<List<Transaction>>

    @POST("transactions")
    suspend fun createTransaction(
        @Body createTransactionRequest: CreateTransactionRequest,
    ): Response<Unit>

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") transactionId: Int
    ): Response<Transaction>

    @PUT("transactions/{id}")
    suspend fun updateTransactionById(
        @Path("id") transactionId: Int,
        @Body updateTransactionRequest: UpdateTransactionRequest,
    ): Response<Transaction>

    @DELETE("transactions/{id}")
    suspend fun deleteTransactionById(
        @Path("id") transactionId: Int
    ): Response<Unit>
}
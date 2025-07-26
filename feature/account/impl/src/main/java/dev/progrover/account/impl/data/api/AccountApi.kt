package dev.progrover.account.impl.data.api

import dev.progrover.account.impl.data.model.ManageAccountRequest
import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.AccountHistory
import dev.progrover.core.base.model.AccountHistoryItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Необходим для получения информации с сервера о счетах
 */
interface AccountApi {

    @GET("accounts")
    suspend fun getAccounts(): Response<List<AccountDetailed>>

    @POST("accounts")
    suspend fun createNewAccount(
        @Body accountRequest: ManageAccountRequest,
    ): Response<AccountDetailed>

    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): Response<AccountDetailed>

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body accountRequest: ManageAccountRequest,
    ): Response<AccountDetailed>

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(
        @Path("id") id: Int
    ): Response<AccountHistory>
}
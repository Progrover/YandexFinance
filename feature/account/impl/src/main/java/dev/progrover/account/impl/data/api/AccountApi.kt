package dev.progrover.account.impl.data.api

import dev.progrover.core.base.model.AccountDetailed
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {

    @GET("accounts")
    suspend fun getAccounts(): List<AccountDetailed>

    @POST("accounts")
    suspend fun createNewAccount(
        @Body name: String,
        @Body balance: String,
        @Body currency: String,
    ): AccountDetailed

    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): AccountDetailed

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body name: String,
        @Body balance: String,
        @Body currency: String,
    ): AccountDetailed

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(
        @Path("id") id: Int
    )
}
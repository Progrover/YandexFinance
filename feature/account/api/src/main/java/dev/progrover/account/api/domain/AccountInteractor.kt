package dev.progrover.account.api.domain

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse

interface AccountInteractor {
    suspend fun getAccounts() : ApiResponse<List<AccountDetailed>>
}
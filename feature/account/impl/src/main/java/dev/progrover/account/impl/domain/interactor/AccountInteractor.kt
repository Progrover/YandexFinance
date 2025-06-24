package dev.progrover.account.impl.domain.interactor

import dev.progrover.core.base.model.AccountDetailed
import dev.progrover.core.base.model.ApiResponse

interface AccountInteractor {
    suspend fun getAccounts() : ApiResponse<List<AccountDetailed>>
}
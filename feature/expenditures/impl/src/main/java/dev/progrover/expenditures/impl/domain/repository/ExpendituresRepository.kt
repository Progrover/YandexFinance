package dev.progrover.expenditures.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.expenditures.api.domain.model.ExpenditureDetailed
import dev.progrover.expenditures.impl.domain.model.Expenditure

interface ExpendituresRepository {

    suspend fun getExpenditures(
        accountId: Int
    ): ApiResponse<List<Expenditure>>

    suspend fun getExpendituresDetailed(
        accountId: Int,
        start: String,
        end: String,
    ): ApiResponse<List<ExpenditureDetailed>>
}
package dev.progrover.history.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.history.impl.domain.model.HistoryElement

interface HistoryRepository {

    suspend fun getHistory(
        accountId: Int,
        start: String? = null,
        end: String? = null,
        type: RouteDesc,
    ): ApiResponse<Pair<String, List<HistoryElement>>>
}
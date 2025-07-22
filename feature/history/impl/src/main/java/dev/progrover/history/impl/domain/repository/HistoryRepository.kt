package dev.progrover.history.impl.domain.repository

import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.history.impl.domain.model.AnalysisElement
import dev.progrover.history.impl.domain.model.HistoryElement

interface HistoryRepository {

    suspend fun getHistory(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc,
    ): ApiResponse<List<HistoryElement>>

    suspend fun getHistoryFromLocalStorage(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc,
    ): ApiResponse<List<HistoryElement>>

    suspend fun getAnalysis(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc,
    ): ApiResponse<List<AnalysisElement>>

    suspend fun getAnalysisFromLocalStorage(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc,
    ): ApiResponse<List<AnalysisElement>>
}
package dev.progrover.history.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.expenditures.api.domain.interactor.ExpendituresInteractor
import dev.progrover.history.impl.data.mapper.HistoryDTOMapper
import dev.progrover.history.impl.domain.model.AnalysisElement
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.history.impl.domain.repository.HistoryRepository
import dev.progrover.incomes.api.domain.interactor.IncomesInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler

class HistoryRepositoryImpl(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    dispatcher: CoroutineDispatcher,
    private val historyDTOMapper: HistoryDTOMapper,
    private val incomesInteractor: IncomesInteractor,
    private val expendituresInteractor: ExpendituresInteractor,
) : HistoryRepository, BaseRepository(
    coroutineExceptionHandler = coroutineExceptionHandler,
    dispatcher = dispatcher,
) {
    override suspend fun getHistory(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc,
    ): ApiResponse<List<HistoryElement>> =
        executeOnIO {
            when (type) {
                RouteDesc.Incomes -> {
                    val response = incomesInteractor.getIncomesDetailed(
                        accountId,
                        start,
                        end,
                    )

                    if (response.value != null) {
                        ApiResponse(
                            value =
                                historyDTOMapper.mapIncomesToHistory(response.value!!),
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }

                RouteDesc.Expenditures -> {
                    val response = expendituresInteractor.getExpendituresDetailed(
                        accountId,
                        start,
                        end,
                    )

                    if (response.value != null) {
                        ApiResponse(
                            value =
                                historyDTOMapper.mapExpendituresToHistory(response.value!!.sortedBy { it.dateTime }),
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }
            }
        }

    override suspend fun getHistoryFromLocalStorage(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc
    ): ApiResponse<List<HistoryElement>> =
        executeOnIO {
            when (type) {
                RouteDesc.Incomes -> {
                    val response = incomesInteractor.getIncomesDetailedFromLocalStorage(
                        accountId,
                        start,
                        end,
                    )

                    if (response.value != null) {
                        ApiResponse(
                            value =
                                historyDTOMapper.mapIncomesToHistory(response.value!!.sortedBy { it.dateTime }),
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }

                RouteDesc.Expenditures -> {
                    val response = expendituresInteractor.getExpendituresDetailedFromLocalStorage(
                        accountId,
                        start,
                        end,
                    )

                    if (response.value != null) {
                        ApiResponse(
                            value =
                                historyDTOMapper.mapExpendituresToHistory(response.value!!.sortedBy { it.dateTime }),
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }
            }
        }

    override suspend fun getAnalysis(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc
    ): ApiResponse<List<AnalysisElement>> =
        executeOnIO {
            var sum = 0
            when (type) {
                RouteDesc.Incomes -> {
                    val response = incomesInteractor.getIncomesDetailed(
                        accountId,
                        start,
                        end,
                    )
                    if (response.value != null) {
                        var analysis =
                            historyDTOMapper.mapIncomesToAnalysis(response.value!!.sortedBy { it.dateTime })
                        if (analysis.isNotEmpty()) {
                            analysis.forEach { sum += it.amount.toDouble().toInt() }
                            if (sum != 0) {
                                analysis =
                                    analysis.map {
                                        it.copy(
                                            percentage = (it.amount.toDouble() / sum * 100).toInt()
                                        )
                                    }
                            }
                        }

                        ApiResponse(
                            value = analysis,
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }

                RouteDesc.Expenditures -> {
                    val response = expendituresInteractor.getExpendituresDetailed(
                        accountId,
                        start,
                        end,
                    )
                    if (response.value != null) {
                        var analysis =
                            historyDTOMapper.mapExpendituresToAnalysis(response.value!!.sortedBy { it.dateTime })
                        if (analysis.isNotEmpty()) {
                            analysis.forEach { sum += it.amount.toDouble().toInt() }
                            if (sum != 0) {
                                analysis =
                                    analysis.map {
                                        it.copy(
                                            percentage = (it.amount.toDouble() / sum * 100).toInt()
                                        )
                                    }
                            }
                        }

                        ApiResponse(
                            value = analysis,
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }
            }
        }

    override suspend fun getAnalysisFromLocalStorage(
        accountId: Int,
        start: String,
        end: String,
        type: RouteDesc
    ): ApiResponse<List<AnalysisElement>> =
        executeOnIO {
            var sum = 0
            when (type) {
                RouteDesc.Incomes -> {
                    val response = incomesInteractor.getIncomesDetailedFromLocalStorage(
                        accountId,
                        start,
                        end,
                    )
                    if (response.value != null) {
                        var analysis =
                            historyDTOMapper.mapIncomesToAnalysis(response.value!!.sortedBy { it.dateTime })
                        if (analysis.isNotEmpty()) {
                            analysis.forEach { sum += it.amount.toDouble().toInt() }
                            analysis =
                                analysis.map {
                                    it.copy(
                                        percentage = (it.amount.toDouble() / sum * 100).toInt()
                                    )
                                }
                        }

                        ApiResponse(
                            value = analysis,
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }

                RouteDesc.Expenditures -> {
                    val response = expendituresInteractor.getExpendituresDetailedFromLocalStorage(
                        accountId,
                        start,
                        end,
                    )
                    if (response.value != null) {
                        var analysis =
                            historyDTOMapper.mapExpendituresToAnalysis(response.value!!.sortedBy { it.dateTime })
                        if (analysis.isNotEmpty()) {
                            analysis.forEach { sum += it.amount.toDouble().toInt() }
                            analysis =
                                analysis.map {
                                    it.copy(
                                        percentage = (it.amount.toDouble() / sum * 100).toInt()
                                    )
                                }
                        }

                        ApiResponse(
                            value = analysis,
                        )
                    } else {
                        ApiResponse(
                            code = response.code,
                            error = response.error,
                        )
                    }
                }
            }
        }
}

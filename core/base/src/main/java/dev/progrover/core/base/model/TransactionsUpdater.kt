package dev.progrover.core.base.model

import dev.progrover.core.base.navigation.RouteDesc
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object TransactionsUpdater {
    private val _updateChannel = MutableSharedFlow<RouteDesc>(extraBufferCapacity = 1)
    val updateChannel = _updateChannel.asSharedFlow()

    fun updateTransactions(transactionsUpdateType: RouteDesc) =
        _updateChannel.tryEmit(transactionsUpdateType)
}
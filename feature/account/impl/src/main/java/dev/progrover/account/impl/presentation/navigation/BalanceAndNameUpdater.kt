package dev.progrover.account.impl.presentation.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object BalanceAndNameUpdater {
    private val _balanceUpdateChannel =
        Channel<String>(Channel.BUFFERED)
    val balanceUpdateChannel = _balanceUpdateChannel.receiveAsFlow()

    fun setBalance(balance: String): Boolean =
        _balanceUpdateChannel.trySend(balance).isSuccess

    private val _nameUpdateChannel =
        Channel<String>(Channel.BUFFERED)
    val nameUpdateChannel = _nameUpdateChannel.receiveAsFlow()

    fun setName(name: String): Boolean =
        _nameUpdateChannel.trySend(name).isSuccess
}
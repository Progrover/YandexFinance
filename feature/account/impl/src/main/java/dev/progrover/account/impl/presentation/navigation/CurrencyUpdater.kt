package dev.progrover.account.impl.presentation.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Класс необходим для отслеживания изменения валюты
 */
object CurrencyUpdater {
    private val _currencyUpdateChannel = Channel<String>(Channel.BUFFERED)
    val currencyUpdateChannel = _currencyUpdateChannel.receiveAsFlow()

    fun setCurrency(currency: String): Boolean =
        _currencyUpdateChannel.trySend(currency).isSuccess
}
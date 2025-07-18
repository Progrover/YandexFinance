package dev.progrover.account.api.domain

import kotlinx.coroutines.CoroutineScope
/**
 * Api интерфейс для получения текущего accountId
 */
interface AccountPropertiesProvider {
    fun getId(scope: CoroutineScope, withResponse: (Result<Int>) -> Unit)
    fun getCurrency() : String
    fun setCurrency(newCurrency: String)
    fun getName() : String
}
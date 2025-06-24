package dev.progrover.account.api.domain

import kotlinx.coroutines.CoroutineScope

/**
 * Api интерфейс для получения текущего accountId
 */
interface AccountIdProvider {
    fun getId(scope: CoroutineScope, withResponse: (Result<Int>) -> Unit)
}
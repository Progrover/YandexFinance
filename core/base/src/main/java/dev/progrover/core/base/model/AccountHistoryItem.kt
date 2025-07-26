package dev.progrover.core.base.model

data class AccountHistoryItem(
    val id: Int,
    val changeType: String,
    val previousState: Account,
    val newState: Account,
    val changeTimestamp: String
)

data class AccountHistory(
    val currentBalance: String,
    val history: List<AccountHistoryItem>
)

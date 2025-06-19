package dev.progrover.history.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.api.domain.AccountInteractor
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.model.Error
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.Variables
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.history.impl.domain.model.HistoryElement
import dev.progrover.history.impl.domain.repository.HistoryRepository
import dev.progrover.history.impl.presentation.contract.history.HistoryUIEffect
import dev.progrover.history.impl.presentation.contract.history.HistoryUIEvent
import dev.progrover.history.impl.presentation.contract.history.HistoryUIState
import dev.progrover.history.impl.presentation.navigation.HistoryNavigationFactory.Companion.ARG_KEY_ROUTE
import dev.progrover.shmr_finance.core.uicommon.R
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val historyRepository: HistoryRepository,
    private val accountInteractor: AccountInteractor,
    private val prefs: Prefs,
) :
    BaseViewModel<HistoryUIEvent, HistoryUIState, HistoryUIEffect>(
        HistoryUIState()
    ) {

    private val itemType: RouteDesc = savedStateHandle[ARG_KEY_ROUTE]!!

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: HistoryUIEvent) =
        when (event) {
            HistoryUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(error = null))

            HistoryUIEvent.OnBackClick ->
                setEffect(HistoryUIEffect.NavigateBack)

            HistoryUIEvent.OnAnalyseClick ->
                setEffect(HistoryUIEffect.ShowError(R.string.in_develop))

            HistoryUIEvent.OnEndClick ->
                setEffect(HistoryUIEffect.ShowError(R.string.in_develop))

            HistoryUIEvent.OnStartClick ->
                setEffect(HistoryUIEffect.ShowError(R.string.in_develop))
        }

    private fun loadInfo() {
        setState(currentState.copy(isLoading = true))
        /**
         * Если нет id аккаунта, сначала пытаемся достать его, а затем запросить историю
         */
        val accountId = prefs.getInt(Variables.CURRENT_ACCOUNT_ID, -1)
        if (accountId == -1) {
            tryMultipleLoad(
                function = { accountInteractor.getAccounts() },
                onSuccess = { result ->
                    result.firstOrNull()?.id?.let {
                        prefs.putInt(Variables.CURRENT_ACCOUNT_ID, it)
                        getHistory(it)
                    } ?: setState(currentState.copy(error = Error.UnknownError))
                },
                onFailure = { error ->
                    setState(
                        currentState.copy(
                            error = error,
                        )
                    )
                }
            )
        } else getHistory(accountId)
    }

    private fun getHistory(accountId: Int) {
        tryMultipleLoad(
            function = {
                historyRepository.getHistory(
                    accountId,
                    type = itemType
                )
            },
            onSuccess = { result ->

                setState(
                    currentState.copy(
                        isLoading = false,
                        history = result.second,
                        currency = result.first,
                        total = countTotalAmount(result.second, result.first),
                    )
                )
            },
            onFailure = { message ->
                setState(
                    currentState.copy(
                        error = message,
                    )
                )
            }
        )
    }

    private fun countTotalAmount(historyItems: List<HistoryElement>, currency: String): String {
        try {
            var total = 0.0
            historyItems.forEach { item ->
                total += item.amount.toDouble()
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Expenditures error in countTotalAmount")
            return "???"
        }
    }
}
package dev.progrover.incomes.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.api.domain.AccountInteractor
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.ServerError
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.Variables
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.incomes.impl.domain.model.Income
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEffect
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEvent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIState
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val incomesRepository: IncomesRepository,
    private val accountInteractor: AccountInteractor,
    private val prefs: Prefs,
    @CoroutineQualifiers.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) :
    BaseViewModel<IncomesUIEvent, IncomesUIState, IncomesUIEffect>(IncomesUIState()) {

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: IncomesUIEvent) =
        when (event) {
            IncomesUIEvent.OnHistoryClick ->
                setEffect(IncomesUIEffect.NavigateToHistoryScreen)

            IncomesUIEvent.OnAllIncomesClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            is IncomesUIEvent.OnIncomeItemClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            IncomesUIEvent.OnAddIncomeClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            IncomesUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(error = null))
        }

    private fun loadInfo() {
        /**
         * Если нет id аккаунта, сначала пытаемся достать его, а затем запросить доходы
         */
        getAccountIdFromPrefs { accountId ->
            if (accountId == -1) {
                tryMultipleLoad(
                    function = { accountInteractor.getAccounts() },
                    onSuccess = { result ->
                        result.firstOrNull()?.id?.let {
                            prefs.putInt(Variables.CURRENT_ACCOUNT_ID, it)
                            getIncomes(it)
                        } ?: setState(currentState.copy(error = ServerError.UnknownError))
                    },
                    onFailure = { error ->
                        setState(
                            currentState.copy(
                                error = error,
                            )
                        )
                    }
                )
            } else getIncomes(accountId)
        }
    }

    private fun countTotalAmount(expenditures: List<Income>, currency: String): String {
        try {
            var total = 0.0
            expenditures.forEach { item ->
                total += item.amount.toDouble()
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Incomes error in countTotalAmount")
            return "???"
        }
    }

    private fun getIncomes(accountId: Int) {
        tryMultipleLoad(
            function = {
                incomesRepository.getIncomes(accountId)
            },
            onSuccess = { result ->

                setState(
                    currentState.copy(
                        isLoading = false,
                        incomes = result.second,
                        currency = result.first,
                        totalIncomes = countTotalAmount(result.second, result.first),
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

    // Костыль сделан, чтобы все соответствовало требованиям задания...
    private fun getAccountIdFromPrefs(callback: (Int) -> Unit) {
        viewModelScope.launch(dispatcher) {
            val id = prefs.getInt(Variables.CURRENT_ACCOUNT_ID, -1)
            callback(id)
        }
    }
}
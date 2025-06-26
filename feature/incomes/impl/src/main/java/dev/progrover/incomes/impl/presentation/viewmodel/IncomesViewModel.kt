package dev.progrover.incomes.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.api.domain.AccountIdProvider
import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.incomes.impl.domain.model.Income
import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEffect
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEvent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIState
import dev.progrover.shmr_finance.core.uicommon.R
import timber.log.Timber
import javax.inject.Inject
/**
 * ViewModel, привязанная к incomes feature
 */
@HiltViewModel
class IncomesViewModel @Inject constructor(
    private val incomesRepository: IncomesRepository,
    private val idProvider: AccountIdProvider,
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
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { getIncomes(it) },
                onFailure = {
                    setState(
                        currentState.copy(
                            error = it as Error,
                        )
                    )
                }
            )
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
}
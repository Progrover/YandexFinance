package dev.progrover.expenditures.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.api.domain.AccountIdProvider
import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.expenditures.impl.domain.model.Expenditure
import dev.progrover.expenditures.impl.domain.repository.ExpendituresRepository
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEffect
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEvent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIState
import dev.progrover.shmr_finance.core.uicommon.R
import timber.log.Timber
import javax.inject.Inject
/**
 * ViewModel, привязанная к expenditures feature
 */
@HiltViewModel
class ExpendituresViewModel @Inject constructor(
    private val expendituresRepository: ExpendituresRepository,
    private val idProvider: AccountIdProvider,
) :
    BaseViewModel<ExpendituresUIEvent, ExpendituresUIState, ExpendituresUIEffect>(
        ExpendituresUIState()
    ) {

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: ExpendituresUIEvent) =
        when (event) {
            ExpendituresUIEvent.OnHistoryClick ->
                setEffect(ExpendituresUIEffect.NavigateToHistoryScreen)

            ExpendituresUIEvent.OnAllExpendituresClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            is ExpendituresUIEvent.OnExpenditureItemClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            ExpendituresUIEvent.OnAddExpenditureClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            ExpendituresUIEvent.OnErrorDialogDone ->
                setState(currentState.copy(error = null))
        }

    private fun loadInfo() {
        setState(currentState.copy(isLoading = true))
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { getExpends(it) },
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

    private fun countTotalAmount(expenditures: List<Expenditure>, currency: String): String {
        try {
            var total = 0.0
            expenditures.forEach { item ->
                total += item.amount.toDouble()
            }

            return total.formatToAmount().addCurrency(currency)
        } catch (e: NumberFormatException) {
            Timber.e("Expenditures error in countTotalAmount")
            return "???"
        }
    }

    private fun getExpends(accountId: Int) {
        tryMultipleLoad(
            function = {
                expendituresRepository.getExpenditures(
                    accountId
                )
            },
            onSuccess = { result ->

                setState(
                    currentState.copy(
                        isLoading = false,
                        currency = result.first,
                        expenditures = result.second,
                        totalExpenditures = countTotalAmount(result.second, result.first),
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
package dev.progrover.expenditures.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.api.domain.AccountInteractor
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.model.Error
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.Variables
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractor
import dev.progrover.expenditures.impl.domain.model.Expenditure
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEffect
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEvent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIState
import dev.progrover.shmr_finance.core.uicommon.R
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExpendituresViewModel @Inject constructor(
    private val expendituresInteractor: ExpendituresInteractor,
    private val accountInteractor: AccountInteractor,
    private val prefs: Prefs,
) :
    BaseViewModel<ExpendituresUIEvent, ExpendituresUIState, ExpendituresUIEffect>(
        ExpendituresUIState()
    ) {

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: ExpendituresUIEvent) =
        when (event) {
            ExpendituresUIEvent.OnRefreshClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

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
        /**
         * Если нет id аккаунта, сначала пытаемся достать его, а затем запросить расходы
         */
        val accountId = prefs.getInt(Variables.CURRENT_ACCOUNT_ID, -1)
        if (accountId == -1) {
            tryMultipleLoad(
                function = { accountInteractor.getAccounts() },
                onSuccess = { result ->
                    result.firstOrNull()?.id?.let {
                        prefs.putInt(Variables.CURRENT_ACCOUNT_ID, it)
                        getExpends(it)
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
        } else getExpends(accountId)
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
                expendituresInteractor.getExpenditures(
                    accountId
                )
            },
            onSuccess = { result ->

                val expenditures = result.second.map { expenditure ->
                    expenditure.copy(
                        amount = expenditure.amount.formatToAmount().addCurrency(result.first)
                    )
                }

                setState(
                    currentState.copy(
                        isLoading = false,
                        expenditures = expenditures,
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
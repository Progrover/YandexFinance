package dev.progrover.incomes.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEffect
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEvent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIState
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class IncomesViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<IncomesUIEvent, IncomesUIState, IncomesUIEffect>(IncomesUIState()) {

    override fun handleUIEvent(event: IncomesUIEvent) =
        when (event) {
            IncomesUIEvent.OnRefreshClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            IncomesUIEvent.OnAllIncomesClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            is IncomesUIEvent.OnIncomeItemClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))

            IncomesUIEvent.OnAddIncomeClick ->
                setEffect(IncomesUIEffect.ShowError(R.string.in_develop))
        }
}
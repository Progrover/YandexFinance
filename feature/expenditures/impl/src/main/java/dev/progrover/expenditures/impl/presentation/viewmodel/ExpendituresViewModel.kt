package dev.progrover.expenditures.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEffect
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEvent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIState
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class ExpendituresViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<ExpendituresUIEvent, ExpendituresUIState, ExpendituresUIEffect>(ExpendituresUIState()) {

    override fun handleUIEvent(event: ExpendituresUIEvent) =
        when (event) {
            ExpendituresUIEvent.OnRefreshClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            ExpendituresUIEvent.OnAllExpendituresClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            is ExpendituresUIEvent.OnIncomeItemClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))

            ExpendituresUIEvent.OnAddIncomeClick ->
                setEffect(ExpendituresUIEffect.ShowError(R.string.in_develop))
        }
}
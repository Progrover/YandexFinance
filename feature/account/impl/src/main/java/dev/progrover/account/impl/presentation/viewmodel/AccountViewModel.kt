package dev.progrover.account.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.account.impl.presentation.contract.account.AccountUIEffect
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<AccountUIEvent, AccountUIState, AccountUIEffect>(AccountUIState()) {

    override fun handleUIEvent(event: AccountUIEvent) =
        when (event) {

            AccountUIEvent.OnAddClick ->
                setEffect(AccountUIEffect.ShowError(dev.progrover.shmr_finance.core.uicommon.R.string.in_develop))

            AccountUIEvent.OnCurrencyClick ->
                setEffect(AccountUIEffect.ShowError(dev.progrover.shmr_finance.core.uicommon.R.string.in_develop))

            AccountUIEvent.OnEditClick ->
                setEffect(AccountUIEffect.ShowError(dev.progrover.shmr_finance.core.uicommon.R.string.in_develop))

            AccountUIEvent.OnTotalAmountClick ->
                setEffect(AccountUIEffect.ShowError(dev.progrover.shmr_finance.core.uicommon.R.string.in_develop))
        }
}
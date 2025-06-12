package dev.progrover.shmr_finance.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.contract.MainUIEffect
import dev.progrover.shmr_finance.contract.MainUIEvent
import dev.progrover.shmr_finance.contract.MainUIState
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,

    ) : BaseViewModel<MainUIEvent, MainUIState, MainUIEffect>(MainUIState()) {

    override fun handleUIEvent(event: MainUIEvent) {}
}
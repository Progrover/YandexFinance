package dev.progrover.shmr_finance.viewmodel

import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.shmr_finance.contract.MainUIEffect
import dev.progrover.shmr_finance.contract.MainUIEvent
import dev.progrover.shmr_finance.contract.MainUIState
import javax.inject.Inject

/**
 * ViewModel, привязанная к MainActivity
 */
class MainActivityViewModel @Inject constructor() :
    BaseViewModel<MainUIEvent, MainUIState, MainUIEffect>(MainUIState()) {

    override fun handleUIEvent(event: MainUIEvent) {}
}
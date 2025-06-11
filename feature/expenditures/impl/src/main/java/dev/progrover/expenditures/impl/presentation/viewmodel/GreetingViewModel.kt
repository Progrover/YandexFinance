package dev.progrover.expenditures.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEffect
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEvent
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIState
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor() :
    BaseViewModel<GreetingUIEvent, GreetingUIState, GreetingUIEffect>(GreetingUIState()) {

    override fun handleUIEvent(event: GreetingUIEvent) {
        when (event) {
            GreetingUIEvent.OnAnimationEnd ->
                setEffect(GreetingUIEffect.NavigateToExpendituresScreen)
        }
    }

}
package dev.progrover.settings.impl.presentation.viewmodel

import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.settings.impl.presentation.contract.haptics.HapticsUIEffect
import dev.progrover.settings.impl.presentation.contract.haptics.HapticsUIEvent
import dev.progrover.settings.impl.presentation.contract.haptics.HapticsUIState
import javax.inject.Inject

class HapticsViewModel @Inject constructor(
    private val prefs: Prefs
) :
    BaseViewModel<HapticsUIEvent, HapticsUIState, HapticsUIEffect>(HapticsUIState()) {

    init {
        setState(currentState.copy(currentChoice = prefs.getVibrationVariant()))
    }

    override fun handleUIEvent(event: HapticsUIEvent) =
        when (event) {
            HapticsUIEvent.OnBackClick ->
                setEffect(HapticsUIEffect.NavigateBack)

            is HapticsUIEvent.OnHapticsClick ->
                setState(
                    currentState.copy(
                        currentChoice = event.newMode,
                        choiseChanged = true
                    )
                )

            HapticsUIEvent.OnConfirmClick -> {
                if (currentState.choiseChanged) {
                    prefs.setVibrationVariant(currentState.currentChoice!!)
                    setEffect(HapticsUIEffect.NavigateBack)
                } else Unit
            }
        }
}
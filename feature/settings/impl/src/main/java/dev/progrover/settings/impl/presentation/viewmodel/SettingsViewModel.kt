package dev.progrover.settings.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEffect
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEvent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    BaseViewModel<SettingsUIEvent, SettingsUIState, SettingsUIEffect>(SettingsUIState()) {

    override fun handleUIEvent(event: SettingsUIEvent) =
        when (event) {
            is SettingsUIEvent.OnSettingsItemClick ->
                setEffect(SettingsUIEffect.ShowError(dev.progrover.shmr_finance.core.uicommon.R.string.in_develop))

            is SettingsUIEvent.OnThemeClick -> {
                setState(currentState.copy(themeModeOn = event.newStatus))
                setEffect(SettingsUIEffect.ShowError(dev.progrover.shmr_finance.core.uicommon.R.string.in_develop))
            }
        }
}
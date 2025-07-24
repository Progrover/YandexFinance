package dev.progrover.settings.impl.presentation.viewmodel

import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.settings.impl.presentation.contract.about.AboutUIEffect
import dev.progrover.settings.impl.presentation.contract.about.AboutUIEvent
import dev.progrover.settings.impl.presentation.contract.about.AboutUIState
import dev.progrover.shmr_finance.core.base.BuildConfig
import javax.inject.Inject

class AboutViewModel @Inject constructor() :
    BaseViewModel<AboutUIEvent, AboutUIState, AboutUIEffect>(AboutUIState()) {

    init {
        setState(currentState.copy(versionName = BuildConfig.VERSION_NAME))
    }

    override fun handleUIEvent(event: AboutUIEvent) {
        when (event) {
            AboutUIEvent.OnBackClick ->
                setEffect(AboutUIEffect.NavigateBack)
        }
    }
}
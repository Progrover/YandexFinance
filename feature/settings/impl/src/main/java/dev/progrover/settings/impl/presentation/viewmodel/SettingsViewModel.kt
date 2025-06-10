package dev.progrover.settings.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEffect
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEvent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIState
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) : BaseViewModel<SettingsUIEvent, SettingsUIState, SettingsUIEffect>(SettingsUIState()) {

    override fun handleUIEvent(event: SettingsUIEvent) =
        when (event) {
            else -> {}
        }
}
package dev.progrover.settings.impl.presentation.viewmodel

import android.content.SharedPreferences
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEffect
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEvent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIState
import dev.progrover.shmr_finance.core.uicommon.R
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val prefs: Prefs
) :
    BaseViewModel<SettingsUIEvent, SettingsUIState, SettingsUIEffect>(SettingsUIState()) {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                "LAST_UPDATE_TIME" -> {
                    setState(
                        currentState.copy(
                            lastTimeUpdate = prefs.getString("LAST_UPDATE_TIME", "") ?: ""
                        )
                    )
                }
            }
        }

    init {
        setState(currentState.copy(lastTimeUpdate = prefs.getString("LAST_UPDATE_TIME", "") ?: ""))
        prefs.registerOnSharedPreferenceChangeListener(
            listener = preferenceChangeListener
        )
    }

    override fun handleUIEvent(event: SettingsUIEvent) =
        when (event) {
            is SettingsUIEvent.OnSettingsItemClick ->
                setEffect(SettingsUIEffect.ShowError(R.string.in_develop))

            is SettingsUIEvent.OnThemeClick -> {
                setState(currentState.copy(themeModeOn = event.newStatus))
                setEffect(SettingsUIEffect.ShowError(R.string.in_develop))
            }
        }

    override fun onCleared() {
        super.onCleared()
        prefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
}
package dev.progrover.shmr_finance.viewmodel

import android.content.SharedPreferences
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.VIBRATION_MODE
import dev.progrover.shmr_finance.contract.MainUIEffect
import dev.progrover.shmr_finance.contract.MainUIEvent
import dev.progrover.shmr_finance.contract.MainUIState
import javax.inject.Inject

/**
 * ViewModel, привязанная к MainActivity
 */
class MainActivityViewModel @Inject constructor(
    private val prefs: Prefs,
) :
    BaseViewModel<MainUIEvent, MainUIState, MainUIEffect>(MainUIState()) {

    private val vibrationListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                VIBRATION_MODE -> {
                    setState(
                        currentState.copy(
                            vibrationMode = prefs.getVibrationVariant()
                        )
                    )
                }
            }
        }

    init {
        prefs.registerOnSharedPreferenceChangeListener(vibrationListener)
        setState(
            currentState.copy(
                vibrationMode = prefs.getVibrationVariant()
            )
        )
    }

    override fun handleUIEvent(event: MainUIEvent) {}
    override fun onCleared() {
        super.onCleared()
        prefs.unregisterOnSharedPreferenceChangeListener(vibrationListener)
    }
}
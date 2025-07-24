package dev.progrover.settings.impl.presentation.viewmodel

import android.content.SharedPreferences
import dev.progrover.core.base.data.storage.SecurePrefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.PIN
import dev.progrover.feature.auth.api.domain.model.NavigationVariant
import dev.progrover.settings.impl.presentation.contract.pin.PinUIEffect
import dev.progrover.settings.impl.presentation.contract.pin.PinUIEvent
import dev.progrover.settings.impl.presentation.contract.pin.PinUIState
import javax.inject.Inject

class PinViewModel @Inject constructor(
    private val securePrefs: SecurePrefs
) :
    BaseViewModel<PinUIEvent, PinUIState, PinUIEffect>(
        PinUIState(
            pinCodeIsInSystem = securePrefs.getFullPinCodeInfo() != null,
            pinCodeModeOn = securePrefs.getFullPinCodeInfo()?.pinCodeModeOn ?: false
        )
    ) {

    private val pinListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == PIN) {
            val pincode = securePrefs.getFullPinCodeInfo()
            setState(
                currentState.copy(
                    pinCodeModeOn = pincode!!.pinCodeModeOn,
                    pinCodeIsInSystem = true
                )
            )
        }
    }

    init {
        securePrefs.registerOnSharedPreferenceChangeListener(pinListener)
    }

    override fun handleUIEvent(event: PinUIEvent) =
        when (event) {
            PinUIEvent.OnBackClick ->
                setEffect(PinUIEffect.NavigateBack)

            PinUIEvent.OnChangePinClick ->
                setEffect(PinUIEffect.NavigateToPinScreen(NavigationVariant.ChangePin))

            PinUIEvent.OnNoPinClick -> {
                securePrefs.setPinCodeMode(false)
                setState(currentState.copy(pinCodeModeOn = false))
            }

            PinUIEvent.OnPinClick -> {
                if (!currentState.pinCodeIsInSystem)
                    setEffect(PinUIEffect.NavigateToPinScreen(NavigationVariant.SetNewPin))
                else {
                    securePrefs.setPinCodeMode(true)
                    setState(currentState.copy(pinCodeModeOn = true))
                }
            }
        }

    override fun onCleared() {
        super.onCleared()
        securePrefs.unregisterOnSharedPreferenceChangeListener(pinListener)
    }
}
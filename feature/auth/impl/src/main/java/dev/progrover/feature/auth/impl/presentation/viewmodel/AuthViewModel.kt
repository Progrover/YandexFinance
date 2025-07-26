package dev.progrover.feature.auth.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.progrover.core.base.data.storage.SecurePrefs
import dev.progrover.core.base.model.PinCode
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.feature.auth.api.domain.model.NavigationVariant
import dev.progrover.feature.auth.impl.domain.model.AuthAlert
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIEffect
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIEvent
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIState
import dev.progrover.feature.auth.impl.presentation.navigation.AuthNavigationFactory.Companion.ARG_KEY_TASK
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel, привязанная к auth screen
 */
class AuthViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val securePrefs: SecurePrefs,
) :
    BaseViewModel<AuthUIEvent, AuthUIState, AuthUIEffect>(
        AuthUIState(savedStateHandle[ARG_KEY_TASK] ?: NavigationVariant.Start)
    ) {

    private var pinCode: PinCode? = null

    init {
        loadInfo()
    }

    override fun handleUIEvent(event: AuthUIEvent) =
        when (event) {
            AuthUIEvent.OnBackClick -> {
                if (currentState.secondStep)
                    setState(currentState.copy(secondStep = false))
                else
                    setEffect(AuthUIEffect.NavigateBack)
            }

            is AuthUIEvent.OnSecondPinCodeChange ->
                secondPinChange(event.newSecondPinCode)

            is AuthUIEvent.OnFirstPinCodeChange ->
                firstPinChange(pin = event.newPinCode)

            AuthUIEvent.OnErrorDialogDone ->
                setState(
                    currentState.copy(
                        alert = null,
                        pinCodeIsCorrect = null,
                    )
                )
        }

    private fun firstPinChange(pin: Int) {
        viewModelScope.launch {
            if (pin.toString().length < 4)
                setState(currentState.copy(pinCode = pin))
            else {
                when (currentState.task) {
                    NavigationVariant.Start -> {
                        if (pin.toString().length == 4) {
                            if (pin == pinCode?.pinCode) {
                                setState(
                                    currentState.copy(
                                        pinCode = pin,
                                        pinCodeIsCorrect = true
                                    )
                                )
                                delay(1000)
                                setEffect(AuthUIEffect.NavigateToExpendsScreen)
                            } else setState(
                                currentState.copy(
                                    pinCodeIsCorrect = false,
                                    pinCode = null,
                                    alert = AuthAlert.IncorrectPinCodeError
                                )
                            )
                        }
                    }

                    else -> {
                        if (pin.toString().length == 4) {
                            setState(
                                currentState.copy(
                                    secondStep = true,
                                    pinCode = pin
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun secondPinChange(pin: Int) {
        viewModelScope.launch {
            if (pin.toString().length < 4)
                setState(currentState.copy(secondPinCode = pin))
            else {
                if (pin.toString().length == 4) {
                    if (pin == currentState.pinCode) {
                        when (currentState.task) {
                            NavigationVariant.Start -> Unit

                            NavigationVariant.SetNewPin ->
                                securePrefs.createPinCode(
                                    PinCode(
                                        pin,
                                        true
                                    )
                                )

                            NavigationVariant.ChangePin ->
                                securePrefs.setPinCodeValue(pin)
                        }
                        setState(
                            currentState.copy(
                                secondPinCode = pin,
                                pinCodeIsCorrect = true
                            )
                        )
                        delay(1000)
                        setEffect(AuthUIEffect.NavigateBack)
                    } else {
                        setState(
                            currentState.copy(
                                alert = AuthAlert.IncorrectSecondPinCodeError,
                                secondPinCode = null,
                                pinCodeIsCorrect = false
                            )
                        )
                    }
                }
            }
        }
    }

    private fun loadInfo() {
        viewModelScope.launch {
            pinCode = securePrefs.getFullPinCodeInfo()
            if ((pinCode?.pinCodeModeOn == false || pinCode == null) && currentState.task == NavigationVariant.Start) {
                setEffect(AuthUIEffect.NavigateToExpendsScreen)
            }
        }
    }
}
package dev.progrover.settings.impl.presentation.viewmodel

import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.CURRENT_MAIN_COLOR
import dev.progrover.core.base.utils.SettingsOptions
import dev.progrover.settings.impl.presentation.contract.color.ColorUIEffect
import dev.progrover.settings.impl.presentation.contract.color.ColorUIEvent
import dev.progrover.settings.impl.presentation.contract.color.ColorUIState
import javax.inject.Inject

class ColorViewModel @Inject constructor(
    private val prefs: Prefs
) :
    BaseViewModel<ColorUIEvent, ColorUIState, ColorUIEffect>(ColorUIState()) {

    override fun handleUIEvent(event: ColorUIEvent) =
        when (event) {
            ColorUIEvent.OnBackClick ->
                setEffect(ColorUIEffect.NavigateBack)

            is ColorUIEvent.OnColorClick ->
                setState(currentState.copy(currentChoice = event.newColor))

            ColorUIEvent.OnConfirmClick -> {
                if (currentState.currentChoice != null) {
                    prefs.putString(
                        CURRENT_MAIN_COLOR,
                        SettingsOptions.colorVariants[currentState.currentChoice]
                    )
                } else Unit
            }
        }
}
package dev.progrover.settings.impl.presentation.contract.color

import dev.progrover.core.base.presentation.mvi.UIEvent
import dev.progrover.core.base.utils.ColorVariant

/**
 * Класс, хранящий все события colors screen
 */
sealed class ColorUIEvent : UIEvent {
    data object OnBackClick : ColorUIEvent()
    data object OnConfirmClick : ColorUIEvent()

    class OnColorClick(val newColor: ColorVariant) : ColorUIEvent()
}
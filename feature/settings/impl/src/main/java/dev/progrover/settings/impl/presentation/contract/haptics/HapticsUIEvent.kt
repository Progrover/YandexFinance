package dev.progrover.settings.impl.presentation.contract.haptics

import dev.progrover.core.base.presentation.mvi.UIEvent
import dev.progrover.core.base.utils.ColorVariant
import dev.progrover.core.base.utils.HapticsVariant

/**
 * Класс, хранящий все события colors screen
 */
sealed class HapticsUIEvent : UIEvent {
    data object OnBackClick : HapticsUIEvent()
    data object OnConfirmClick : HapticsUIEvent()

    class OnHapticsClick(val newMode: HapticsVariant) : HapticsUIEvent()
}
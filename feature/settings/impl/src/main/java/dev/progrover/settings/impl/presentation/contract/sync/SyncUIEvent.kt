package dev.progrover.settings.impl.presentation.contract.sync

import dev.progrover.core.base.presentation.mvi.UIEvent

/**
 * Класс, хранящий все события sync screen
 */
sealed class SyncUIEvent : UIEvent {
    data object OnBackClick : SyncUIEvent()

    class OnTimeChange(val newTime: Float) : SyncUIEvent()
}
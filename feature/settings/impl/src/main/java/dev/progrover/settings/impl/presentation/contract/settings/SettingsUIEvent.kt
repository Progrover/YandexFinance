package dev.progrover.settings.impl.presentation.contract.settings

import dev.progrover.core.base.presentation.mvi.UIEvent
import dev.progrover.settings.impl.domain.model.ChapterRoute

sealed class SettingsUIEvent : UIEvent {
    class OnSettingsItemClick(val route: ChapterRoute): SettingsUIEvent()
    class OnThemeClick(val newStatus: Boolean): SettingsUIEvent()
}
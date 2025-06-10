package dev.progrover.settings.impl.presentation.contract.settings

import dev.progrover.core.base.presentation.mvi.UIState

data class SettingsUIState(
    val isLoading: Boolean = false,
) : UIState
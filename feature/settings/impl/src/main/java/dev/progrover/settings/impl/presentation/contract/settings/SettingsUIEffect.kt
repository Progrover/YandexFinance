package dev.progrover.settings.impl.presentation.contract.settings

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты settings feature
 */
sealed class SettingsUIEffect : UIEffect {
    data object NavigateToColorsScreen : SettingsUIEffect()
    data object NavigateToHapticsScreen : SettingsUIEffect()
    data object NavigateToLanguageScreen : SettingsUIEffect()
    data object NavigateToPinScreen : SettingsUIEffect()

    class ShowError(@StringRes val messageResId: Int) : SettingsUIEffect()
}
package dev.progrover.settings.impl.presentation.contract.settings

import androidx.annotation.StringRes
import dev.progrover.core.base.presentation.mvi.UIEffect

sealed class SettingsUIEffect : UIEffect {

    class ShowError(@StringRes val messageResId: Int) : SettingsUIEffect()
}
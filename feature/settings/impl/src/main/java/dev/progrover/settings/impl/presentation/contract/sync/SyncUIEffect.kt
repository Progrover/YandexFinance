package dev.progrover.settings.impl.presentation.contract.sync

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты sync screen
 */
sealed class SyncUIEffect : UIEffect {
    data object NavigateBack : SyncUIEffect()
}
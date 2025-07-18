package dev.progrover.feature.edit.impl.presentation.contract.edit

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты edit feature
 */
sealed class EditUIEffect : UIEffect {
    data object NavigateBack : EditUIEffect()
}
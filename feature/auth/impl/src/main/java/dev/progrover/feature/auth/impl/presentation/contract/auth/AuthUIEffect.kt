package dev.progrover.feature.auth.impl.presentation.contract.auth

import dev.progrover.core.base.presentation.mvi.UIEffect

/**
 * Класс, хранящий все эффекты auth screen
 */
sealed class AuthUIEffect : UIEffect {
    data object NavigateBack : AuthUIEffect()
    data object NavigateToExpendsScreen : AuthUIEffect()
}
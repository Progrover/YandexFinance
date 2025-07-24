package dev.progrover.settings.impl.presentation.contract.pin

import dev.progrover.core.base.presentation.mvi.UIEffect
import dev.progrover.feature.auth.api.domain.model.NavigationVariant

/**
 * Класс, хранящий все эффекты pin screen
 */
sealed class PinUIEffect : UIEffect {
    data object NavigateBack : PinUIEffect()

    class NavigateToPinScreen(val type: NavigationVariant) : PinUIEffect()
}
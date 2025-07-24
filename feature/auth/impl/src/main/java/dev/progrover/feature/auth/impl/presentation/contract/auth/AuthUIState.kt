package dev.progrover.feature.auth.impl.presentation.contract.auth

import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.feature.auth.api.domain.model.NavigationVariant

/**
 * Класс, необходимый для отслеживания состояния auth screen
 */
data class AuthUIState(
    val task: NavigationVariant,
    val secondStep: Boolean = false, // для подтверждения пин-кода
    val pinCode: Int? = null, //
    val secondPinCode: Int? = null,
    val pinCodeIsCorrect: Boolean? = null, // для изменения цвета плашек
    val alert: Alert? = null,
) : UIState
package dev.progrover.settings.impl.presentation.contract.language

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.LocaleVariant

/**
 * Класс, необходимый для отслеживания состояния colors screen
 */
data class LanguageUIState(
    val isLoading: Boolean = false,
    val currentChoice: LocaleVariant? = null,
) : UIState
package dev.progrover.settings.impl.presentation.contract.about

import dev.progrover.core.base.presentation.mvi.UIState

/**
 * Класс, необходимый для отслеживания состояния about screen
 */
data class AboutUIState(
    val versionName: String = "",
) : UIState
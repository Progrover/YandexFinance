package dev.progrover.settings.impl.presentation.contract.sync

import dev.progrover.core.base.presentation.mvi.UIState

/**
 * Класс, необходимый для отслеживания состояния sync screen
 */
data class SyncUIState(
    val time: Float,
) : UIState {
    val timeForPresentation = time.toInt()
}
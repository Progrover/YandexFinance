package dev.progrover.shmr_finance.contract

import dev.progrover.core.base.presentation.mvi.UIState
/**
 * Класс, необходимый для отслеживания состояния MainActivity
 */
data class MainUIState(
    val isBottomNavigationBarVisible: Boolean = true,
) : UIState
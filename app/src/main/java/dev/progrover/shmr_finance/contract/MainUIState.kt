package dev.progrover.shmr_finance.contract

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.core.base.utils.HapticsVariant

/**
 * Класс, необходимый для отслеживания состояния MainActivity
 */
data class MainUIState(
    val isBottomNavigationBarVisible: Boolean = true,
    val vibrationMode: HapticsVariant = HapticsVariant.Silent
) : UIState
package dev.progrover.shmr_finance.contract

import dev.progrover.core.base.presentation.mvi.UIState

data class MainUIState(
    val isBottomNavigationBarVisible: Boolean = true,
) : UIState
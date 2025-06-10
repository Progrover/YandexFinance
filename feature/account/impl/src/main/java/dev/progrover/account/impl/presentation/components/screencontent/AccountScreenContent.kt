package dev.progrover.account.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState

@Composable
internal fun AccountScreenContent(
    modifier: Modifier,
    uiState: AccountUIState,
    onEvent: (AccountUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {}
}
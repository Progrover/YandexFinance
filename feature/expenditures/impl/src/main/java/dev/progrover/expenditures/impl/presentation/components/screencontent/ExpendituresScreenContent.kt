package dev.progrover.expenditures.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEvent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIState

@Composable
internal fun ExpendituresScreenContent(
    modifier: Modifier,
    uiState: ExpendituresUIState,
    onEvent: (ExpendituresUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {}
}
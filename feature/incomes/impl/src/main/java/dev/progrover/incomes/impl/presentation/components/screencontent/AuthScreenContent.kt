package dev.progrover.incomes.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.progrover.core.theme.AppTheme
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEvent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIState

@Composable
internal fun IncomesScreenContent(
    modifier: Modifier,
    uiState: IncomesUIState,
    onEvent: (IncomesUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface),
    ) {

    }
}
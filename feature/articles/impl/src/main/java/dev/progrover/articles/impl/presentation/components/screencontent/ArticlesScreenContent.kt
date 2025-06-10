package dev.progrover.articles.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEvent
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIState

@Composable
internal fun ArticlesScreenContent(
    modifier: Modifier,
    uiState: ArticlesUIState,
    onEvent: (ArticlesUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {}
}
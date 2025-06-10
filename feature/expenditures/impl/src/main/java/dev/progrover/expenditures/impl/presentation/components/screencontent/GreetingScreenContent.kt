package dev.progrover.expenditures.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.progrover.core.theme.AppTheme
import dev.progrover.expenditures.impl.presentation.components.AppLogo
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEvent
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIState

@Composable
internal fun GreetingScreenContent(
    modifier: Modifier,
    uiState: GreetingUIState,
    onEvent: (GreetingUIEvent) -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface),
    ) {

        AppLogo(
            modifier = Modifier
                .align(Alignment.Center),
            isLogoVisible = uiState.isLogoVisible,
            isTextVisible = uiState.isTextVisible,
        )
    }
}
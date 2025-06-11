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

@Composable
internal fun GreetingScreenContent(
    modifier: Modifier,
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
            afterAnimation = { onEvent(GreetingUIEvent.OnAnimationEnd) }
        )
    }
}
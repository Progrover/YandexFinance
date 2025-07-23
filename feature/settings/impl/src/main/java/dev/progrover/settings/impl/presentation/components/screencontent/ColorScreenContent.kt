package dev.progrover.settings.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.progrover.core.base.utils.ColorVariant
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.components.ColorItem
import dev.progrover.settings.impl.presentation.contract.color.ColorUIEvent
import dev.progrover.settings.impl.presentation.contract.color.ColorUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun ColorScreenContent(
    modifier: Modifier,
    uiState: ColorUIState,
    onEvent: (ColorUIEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
    ) {
        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            toolbar = {

                DefaultToolbar(
                    modifier = Modifier,
                    backgroundColor = AppTheme.colors.surface,
                    title = stringResource(R.string.color),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(ColorUIEvent.OnBackClick) }
                )
            },
        ) {

            ColorItem(
                uiState = uiState,
                colorVariant = ColorVariant.Green,
                title = R.string.green,
                color = Color(0xFF2AE881),
                onClick = { color -> onEvent(ColorUIEvent.OnColorClick(color)) }
            )

            ColorItem(
                uiState = uiState,
                colorVariant = ColorVariant.Blue,
                title = R.string.blue,
                color = Color(0xFF2AC2E8),
                onClick = { color -> onEvent(ColorUIEvent.OnColorClick(color)) }
            )

            ColorItem(
                uiState = uiState,
                colorVariant = ColorVariant.Purple,
                title = R.string.purple,
                color = Color(0xFFDB2AE8),
                onClick = { color -> onEvent(ColorUIEvent.OnColorClick(color)) }
            )

            ColorItem(
                uiState = uiState,
                colorVariant = ColorVariant.Orange,
                title = R.string.orange,
                color = Color(0xFFE87C2A),
                onClick = { color -> onEvent(ColorUIEvent.OnColorClick(color)) }
            )

            ColorItem(
                uiState = uiState,
                colorVariant = ColorVariant.Yellow,
                title = R.string.yellow,
                color = Color(0xFFDBE82A),
                onClick = { color -> onEvent(ColorUIEvent.OnColorClick(color)) }
            )
        }

        DefaultFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(AppTheme.paddings.padding16),
            text = stringResource(R.string.confirm),
            cornerRadius = AppTheme.sizes.size8,
            innerVerticalPadding = AppTheme.paddings.padding8,
            backgroundColor = if (uiState.currentChoice != null)
                AppTheme.colors.main
            else AppTheme.colors.main.copy(0.6f),
            onClick = { onEvent(ColorUIEvent.OnConfirmClick) }
        )
    }
}
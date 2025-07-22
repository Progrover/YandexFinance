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
import dev.progrover.core.uicommon.views.CustomListItem
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.core.uicommon.views.DefaultToolbar
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
            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.green),
                backgroundColor = if (uiState.currentChoice == ColorVariant.Green)
                    Color(0xFF2AE881).copy(alpha = 0.3f) else AppTheme.colors.surface,
                titleColor = AppTheme.colors.textSecondary,
                customElement = {},
                onClick = { onEvent(ColorUIEvent.OnColorClick(ColorVariant.Green)) }
            )

            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.blue),
                backgroundColor = if (uiState.currentChoice == ColorVariant.Blue)
                    Color(0xFF2AC2E8).copy(alpha = 0.3f) else AppTheme.colors.surface,
                titleColor = AppTheme.colors.textSecondary,
                customElement = {},
                onClick = { onEvent(ColorUIEvent.OnColorClick(ColorVariant.Blue)) }
            )

            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.purple),
                backgroundColor = if (uiState.currentChoice == ColorVariant.Purple)
                    Color(0xFFDB2AE8).copy(alpha = 0.3f) else AppTheme.colors.surface,
                titleColor = AppTheme.colors.textSecondary,
                customElement = { },
                onClick = { onEvent(ColorUIEvent.OnColorClick(ColorVariant.Purple)) }
            )

            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.orange),
                backgroundColor = if (uiState.currentChoice == ColorVariant.Orange)
                    Color(0xFFE87C2A).copy(alpha = 0.3f) else AppTheme.colors.surface,
                titleColor = AppTheme.colors.textSecondary,
                customElement = { },
                onClick = { onEvent(ColorUIEvent.OnColorClick(ColorVariant.Orange)) }
            )

            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.yellow),
                backgroundColor = if (uiState.currentChoice == ColorVariant.Yellow)
                    Color(0xFFDBE82A).copy(alpha = 0.3f) else AppTheme.colors.surface,
                titleColor = AppTheme.colors.textSecondary,
                customElement = { },
                onClick = { onEvent(ColorUIEvent.OnColorClick(ColorVariant.Yellow)) }
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
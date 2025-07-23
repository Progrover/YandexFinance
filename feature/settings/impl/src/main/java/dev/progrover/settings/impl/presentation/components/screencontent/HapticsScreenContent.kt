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
import androidx.compose.ui.res.stringResource
import dev.progrover.core.base.utils.HapticsVariant
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.components.HapticsItem
import dev.progrover.settings.impl.presentation.contract.haptics.HapticsUIEvent
import dev.progrover.settings.impl.presentation.contract.haptics.HapticsUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun HapticsScreenContent(
    modifier: Modifier,
    uiState: HapticsUIState,
    onEvent: (HapticsUIEvent) -> Unit,
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
                    title = stringResource(R.string.haptics),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(HapticsUIEvent.OnBackClick) }
                )
            },
        ) {

            HapticsItem(
                modifier = Modifier,
                title = R.string.no_vibration,
                uiState = uiState,
                onClick = { variant -> onEvent(HapticsUIEvent.OnHapticsClick(variant)) },
                vibrationVariant = HapticsVariant.Silent
            )

            HapticsItem(
                modifier = Modifier,
                title = R.string.vibration_low,
                uiState = uiState,
                onClick = { variant -> onEvent(HapticsUIEvent.OnHapticsClick(variant)) },
                vibrationVariant = HapticsVariant.Short
            )

            HapticsItem(
                modifier = Modifier,
                title = R.string.vibration_medium,
                uiState = uiState,
                onClick = { variant -> onEvent(HapticsUIEvent.OnHapticsClick(variant)) },
                vibrationVariant = HapticsVariant.Medium
            )

            HapticsItem(
                modifier = Modifier,
                title = R.string.vibration_high,
                uiState = uiState,
                onClick = { variant -> onEvent(HapticsUIEvent.OnHapticsClick(variant)) },
                vibrationVariant = HapticsVariant.Long
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
            backgroundColor = if (uiState.choiseChanged)
                AppTheme.colors.main
            else AppTheme.colors.main.copy(0.6f),
            onClick = { onEvent(HapticsUIEvent.OnConfirmClick) }
        )
    }
}
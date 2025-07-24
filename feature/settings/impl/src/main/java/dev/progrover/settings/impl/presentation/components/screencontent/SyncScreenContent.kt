package dev.progrover.settings.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.contract.sync.SyncUIEvent
import dev.progrover.settings.impl.presentation.contract.sync.SyncUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun SyncScreenContent(
    modifier: Modifier,
    uiState: SyncUIState,
    onEvent: (SyncUIEvent) -> Unit,
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
                    title = stringResource(R.string.sync),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(SyncUIEvent.OnBackClick) }
                )
            },
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.paddings.padding16,
                        top = AppTheme.paddings.padding68,
                        end = AppTheme.paddings.padding16,
                        bottom = AppTheme.paddings.padding40
                    ),
                color = AppTheme.colors.textMain,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.titleLarge,
                text = stringResource(
                    R.string.current_sync_time
                ) + " ${uiState.timeForPresentation} " + stringResource(R.string.hours)
            )

            Slider(
                modifier = Modifier
                    .padding(horizontal = AppTheme.paddings.padding20)
                    .fillMaxWidth(),
                value = uiState.time,
                onValueChange = { newTime -> onEvent(SyncUIEvent.OnTimeChange(newTime)) },
                colors = SliderDefaults.colors(
                    thumbColor = AppTheme.colors.main,
                    activeTrackColor = AppTheme.colors.main,
                    inactiveTrackColor = AppTheme.colors.secondary,
                ),
                steps = 24,
                valueRange = 1f..24f
            )
        }
    }
}
package dev.progrover.settings.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomListItem
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEvent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun SettingsScreenContent(
    modifier: Modifier,
    uiState: SettingsUIState,
    onEvent: (SettingsUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {

        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            toolbar = {

                DefaultToolbar(
                    modifier = Modifier,
                    title = stringResource(R.string.settings_title),
                )
            },
        ) {

            CustomListItem(
                modifier = Modifier,
                backgroundColor = AppTheme.colors.containerHigh,
                title = stringResource(R.string.theme),
                titleColor = AppTheme.colors.textSecondary,
                customElement = {

                    Switch(
                        modifier = Modifier
                            .padding(vertical = AppTheme.paddings.padding4)
                            .height(32.dp),
                        checked = uiState.themeModeOn,
                        onCheckedChange = { newStatus ->
                            onEvent(SettingsUIEvent.OnThemeClick(newStatus))
                        },
                        colors = SwitchDefaults.colors(
                            checkedBorderColor = AppTheme.colors.brightGreen,
                            uncheckedBorderColor = AppTheme.colors.outline,
                            checkedThumbColor = AppTheme.colors.brightGreen,
                            checkedTrackColor = AppTheme.colors.paleGreen,
                            uncheckedThumbColor = AppTheme.colors.outline,
                            uncheckedTrackColor = AppTheme.colors.surfaceContainerHigh,
                        )
                    )
                },
            )

            uiState.chapters.forEach { chapter ->
                DefaultListItem(
                    modifier = Modifier,
                    title = stringResource(chapter.titleResId),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    endIconResId = R.drawable.right_triangle,
                    onClick = { onEvent(SettingsUIEvent.OnSettingsItemClick(chapter.route)) }
                )
            }
        }

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )
    }
}
package dev.progrover.settings.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.contract.pin.PinUIEvent
import dev.progrover.settings.impl.presentation.contract.pin.PinUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun PinScreenContent(
    modifier: Modifier,
    uiState: PinUIState,
    onEvent: (PinUIEvent) -> Unit,
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
                    title = stringResource(R.string.password),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(PinUIEvent.OnBackClick) }
                )
            },
        ) {
            Row {

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = AppTheme.paddings.padding16)
                        .size(AppTheme.sizes.size16)
                        .clip(CircleShape)
                        .background(
                            if (!uiState.pinCodeModeOn)
                                AppTheme.colors.textSecondary else
                                AppTheme.colors.textSecondary.copy(
                                    alpha = 0.5f
                                )
                        )
                )

                DefaultListItem(
                    modifier = Modifier,
                    verticalTextPadding = AppTheme.paddings.padding12,
                    title = stringResource(R.string.pincode_mode_off),
                    dividerVisible = false,
                    titleColor = if (!uiState.pinCodeModeOn)
                        AppTheme.colors.textSecondary else
                        AppTheme.colors.textSecondary.copy(
                            alpha = 0.5f
                        ),
                    onClick = { onEvent(PinUIEvent.OnNoPinClick) }
                )
            }

            Row {

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = AppTheme.paddings.padding16)
                        .size(AppTheme.sizes.size16)
                        .clip(CircleShape)
                        .background(
                            if (uiState.pinCodeModeOn)
                                AppTheme.colors.textSecondary else
                                AppTheme.colors.textSecondary.copy(
                                    alpha = 0.5f
                                )
                        )
                )

                DefaultListItem(
                    modifier = Modifier,
                    verticalTextPadding = AppTheme.paddings.padding12,
                    title = stringResource(R.string.pincode_mode_on),
                    dividerVisible = false,
                    titleColor = if (uiState.pinCodeModeOn)
                        AppTheme.colors.textSecondary else
                        AppTheme.colors.textSecondary.copy(
                            alpha = 0.5f
                        ),
                    onClick = { onEvent(PinUIEvent.OnPinClick) }
                )
            }
        }

        if (uiState.pinCodeModeOn && uiState.pinCodeIsInSystem)
            DefaultFloatingButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(AppTheme.paddings.padding16),
                text = stringResource(R.string.change_pin),
                cornerRadius = AppTheme.sizes.size8,
                innerVerticalPadding = AppTheme.paddings.padding8,
                backgroundColor = AppTheme.colors.main,
                onClick = { onEvent(PinUIEvent.OnChangePinClick) }
            )
    }
}
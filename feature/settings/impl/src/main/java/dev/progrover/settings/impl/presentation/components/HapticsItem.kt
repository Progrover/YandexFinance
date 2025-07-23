package dev.progrover.settings.impl.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import dev.progrover.core.base.utils.HapticsVariant
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.settings.impl.presentation.contract.haptics.HapticsUIState

@Composable
internal fun HapticsItem(
    modifier: Modifier,
    uiState: HapticsUIState,
    @StringRes title: Int,
    vibrationVariant: HapticsVariant,
    onClick: (HapticsVariant) -> Unit,
) {
    Row(
        modifier = modifier
            .noRippleClickable(
                onClick = { onClick(vibrationVariant) },
                vibration = vibrationVariant
            )
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = AppTheme.paddings.padding16)
                .size(AppTheme.sizes.size16)
                .clip(CircleShape)
                .background(
                    if (uiState.currentChoice == vibrationVariant)
                        AppTheme.colors.textSecondary else
                        AppTheme.colors.textSecondary.copy(alpha = 0.5f)
                )
        )

        DefaultListItem(
            modifier = Modifier,
            verticalTextPadding = AppTheme.paddings.padding12,
            title = stringResource(title),
            dividerVisible = false,
            titleColor = if (uiState.currentChoice == vibrationVariant)
                AppTheme.colors.textSecondary else AppTheme.colors.textSecondary.copy(alpha = 0.5f),
        )
    }
}
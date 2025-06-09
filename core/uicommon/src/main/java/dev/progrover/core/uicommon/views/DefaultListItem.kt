package dev.progrover.core.uicommon.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable

@Composable
fun DefaultListItem(
    modifier: Modifier,
    horizontalPadding: Dp = AppTheme.paddings.padding16,
    verticalPadding: Dp = AppTheme.paddings.padding24,
    title: String,
    captionTitle: String?,
    captionAdditional: String?,
    additionalText: String?,
    startIconResId: Int?,
    endIconResId: Int?,
    onClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .noRippleClickable { onClick() }
            .fillMaxWidth(),
    ) {

        Row(
            modifier = Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding,
                )
        ) {

            if (startIconResId != null) {

                Image(
                    modifier = Modifier
                        .padding(end = AppTheme.paddings.padding16)
                        .size(AppTheme.sizes.size24),
                    painter = painterResource(startIconResId),
                    contentDescription = null,
                )
            }

            if (captionTitle != null) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {

                    Text(
                        modifier = Modifier,
                        text = title,
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.bodyLarge,
                    )

                    Text(
                        modifier = Modifier,
                        text = captionTitle,
                        color = AppTheme.colors.textSecondary,
                        style = AppTheme.typography.labelMedium,
                    )
                }
            } else {

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = title,
                    color = AppTheme.colors.textMain,
                    style = AppTheme.typography.bodyLarge,
                )
            }

            if (additionalText != null) {

                if (captionAdditional != null) {

                    Column(
                        modifier = Modifier
                            .weight(1f),
                    ) {

                        Text(
                            modifier = Modifier,
                            text = additionalText,
                            color = AppTheme.colors.textMain,
                            style = AppTheme.typography.bodyLarge,
                        )

                        Text(
                            modifier = Modifier,
                            text = captionAdditional,
                            color = AppTheme.colors.textSecondary,
                            style = AppTheme.typography.labelMedium,
                        )
                    }
                } else {

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = additionalText,
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.bodyLarge,
                    )
                }
            }

            if (endIconResId != null) {

                Image(
                    modifier = Modifier
                        .padding(start = AppTheme.paddings.padding16)
                        .size(AppTheme.sizes.size24),
                    painter = painterResource(endIconResId),
                    contentDescription = null,
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = AppTheme.colors.border,
        )
    }

}
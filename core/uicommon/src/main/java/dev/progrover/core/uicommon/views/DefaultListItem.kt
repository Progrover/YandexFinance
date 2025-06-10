package dev.progrover.core.uicommon.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable

@Composable
fun DefaultListItem(
    modifier: Modifier,
    title: String,
    backgroundColor: Color = AppTheme.colors.surface,
    iconBackgroundColor: Color = AppTheme.colors.paleGreen,
    captionTitle: String? = null,
    captionAdditional: String? = null,
    additionalText: String? = null,
    startIcon: String? = null,
    endIconResId: Int? = null,
    horizontalPadding: Dp = AppTheme.paddings.padding16,
    verticalPadding: Dp = AppTheme.paddings.padding8,
    onClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .noRippleClickable { onClick() }
            .fillMaxWidth()
            .background(backgroundColor),
    ) {

        Row(
            modifier = Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding,
                ),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding16)
        ) {

            if (startIcon != null) {

                Box(
                    modifier = Modifier
                        .size(AppTheme.sizes.size24)
                        .clip(CircleShape)
                        .background(iconBackgroundColor)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center,
                ) {

                    Text(
                        modifier = Modifier,
                        text = startIcon,
                        style = AppTheme.typography.emoji,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
            ) {

                Text(
                    modifier = Modifier,
                    text = title,
                    color = AppTheme.colors.textMain,
                    style = AppTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (captionTitle != null) {

                    Text(
                        modifier = Modifier,
                        text = captionTitle,
                        color = AppTheme.colors.textSecondary,
                        style = AppTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            if (additionalText != null) {

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                ) {

                    Text(
                        modifier = Modifier,
                        text = additionalText,
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                    )

                    if (captionAdditional != null) {

                        Text(
                            modifier = Modifier,
                            text = captionAdditional,
                            color = AppTheme.colors.textMain,
                            style = AppTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End,
                        )
                    }
                }
            }

            if (endIconResId != null) {

                Image(
                    modifier = Modifier
                        .size(AppTheme.sizes.size24)
                        .align(Alignment.CenterVertically),
                    imageVector = ImageVector.vectorResource(endIconResId),
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
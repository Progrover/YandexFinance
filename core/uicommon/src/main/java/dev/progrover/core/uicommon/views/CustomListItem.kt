package dev.progrover.core.uicommon.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.progrover.core.base.utils.isUnicode
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.utils.noRippleClickable

@Composable
fun CustomListItem(
    modifier: Modifier,
    title: String,
    titleColor: Color = AppTheme.colors.textMain,
    backgroundColor: Color = AppTheme.colors.surface,
    horizontalPadding: Dp = AppTheme.paddings.padding16,
    verticalPadding: Dp = AppTheme.paddings.padding8,
    customElement: @Composable () -> Unit,
    startIcon: String? = null,
    dividerVisible: Boolean = true,
    iconBackgroundColor: Color = AppTheme.colors.paleGreen,
    onClick: (() -> Unit)? = null,
) {
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .conditionally(
                onClick != null,
                trueExtension = { noRippleClickable { onClick!!.invoke() } }
            )
            .fillMaxWidth()
            .background(backgroundColor),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!startIcon.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .padding(end = AppTheme.paddings.padding16)
                        .size(AppTheme.sizes.size24)
                        .clip(CircleShape)
                        .background(iconBackgroundColor)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier,
                        text = startIcon,
                        style = when (startIcon.isUnicode()) {
                            true -> AppTheme.typography.emoji.copy(
                                fontSize = with(density) {
                                    (AppTheme.typography.emoji.fontSize.value / fontScale).sp
                                }
                            )

                            false -> AppTheme.typography.emoji.copy(
                                fontSize = with(density) {
                                    (10 / fontScale).sp
                                }
                            )
                        },
                    )
                }
            }

            Text(
                modifier = Modifier
                    .weight(1f),
                text = title,
                color = titleColor,
                style = AppTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Box(modifier = Modifier.weight(3f)) { customElement() }

        }

        if (dividerVisible) {
            HorizontalDivider(
                thickness = 1.dp,
                color = AppTheme.colors.border,
            )
        }
    }
}
package dev.progrover.core.uicommon.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    onClick: (() -> Unit)? = null,
) {

    Column(
        modifier = modifier
            .conditionally(
                onClick != null,
                trueExtension = { noRippleClickable { onClick!!.invoke() } })
            .fillMaxWidth()
            .background(backgroundColor),
    ) {

        Row(
            modifier = Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding,
                ),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding16),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                modifier = Modifier,
                text = title,
                color = titleColor,
                style = AppTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.weight(1f))

            customElement()
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = AppTheme.colors.border,
        )
    }
}
package dev.progrover.core.uicommon.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable

@Composable
fun DefaultFloatingButton(
    modifier: Modifier,
    text: String,
    cornerRadius: Dp = 100.dp,
    textColor: Color = AppTheme.colors.textMain,
    textStyle: TextStyle = AppTheme.typography.titleMedium,
    innerVerticalPadding: Dp = 0.dp,
    innerHorizontalPadding: Dp = AppTheme.paddings.padding10,
    backgroundColor: Color = AppTheme.colors.brightGreen,
    onClick: () -> Unit = {},
) {

    Text(
        modifier = modifier
            .noRippleClickable { onClick() }
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .padding(
                horizontal = innerHorizontalPadding,
                vertical = innerVerticalPadding
            ),
        text = text,
        color = textColor,
        style = textStyle,
    )
}
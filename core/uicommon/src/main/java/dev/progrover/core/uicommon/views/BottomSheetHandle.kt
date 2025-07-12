package dev.progrover.core.uicommon.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme

@Composable
fun BottomSheetHandle(
    modifier: Modifier,
    color: Color = AppTheme.colors.outline,
    shape: RoundedCornerShape = RoundedCornerShape(100.dp),
) {

    Box(
        modifier = modifier
            .clip(shape)
            .background(color),
    )
}
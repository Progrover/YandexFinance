package dev.progrover.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppCorners(
    val cornersX1: Dp = 1.dp,
    val cornersX2: Dp = 2.dp,
    val cornersX3: Dp = 3.dp,
    val cornersX4: Dp = 4.dp,
    val cornersX5: Dp = 5.dp,
    val cornersX6: Dp = 6.dp,
    val cornersX8: Dp = 8.dp,
    val cornersX10: Dp = 10.dp,
    val cornersX24: Dp = 24.dp,
    val cornersX30: Dp = 30.dp,
    val cornersX60: Dp = 60.dp,
    val corners16: Dp = 16.dp,
)

internal val LocalAppCorners = staticCompositionLocalOf { AppCorners() }
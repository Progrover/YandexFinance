package dev.progrover.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppPaddings(
    val extraSmall: Dp = 3.dp,
    val paddingX1: Dp = 5.dp,
    val paddingX2: Dp = 10.dp,
    val paddingX3: Dp = 15.dp,
    val paddingX4: Dp = 20.dp,
    val paddingX5: Dp = 25.dp,
    val paddingX6: Dp = 30.dp,
    val paddingX7: Dp = 35.dp,
    val paddingX8: Dp = 40.dp,
    val paddingX9: Dp = 45.dp,
    val paddingX10: Dp = 50.dp,
    val paddingX11: Dp = 55.dp,
    val paddingX12: Dp = 60.dp,
    val paddingX14: Dp = 70.dp,
    val paddingX16: Dp = 80.dp,
    val paddingX18: Dp = 90.dp,
    val paddingX20: Dp = 100.dp,
    val padding1: Dp = 1.dp,
    val padding2: Dp = 2.dp,
    val padding3: Dp = 3.dp,
    val padding4: Dp = 4.dp,
    val padding5: Dp = 5.dp,
    val padding6: Dp = 6.dp,
    val padding8: Dp = 8.dp,
    val padding10: Dp = 10.dp,
    val padding12: Dp = 12.dp,
    val padding14: Dp = 14.dp,
    val padding16: Dp = 16.dp,
    val padding18: Dp = 18.dp,
    val padding20: Dp = 20.dp,
    val padding23: Dp = 24.dp,
    val padding24: Dp = 24.dp,
    val padding28: Dp = 28.dp,
    val padding32: Dp = 32.dp,
    val padding40: Dp = 40.dp,
    val padding54: Dp = 54.dp,
    val padding68: Dp = 68.dp,
    val padding76: Dp = 76.dp,
    val padding265: Dp = 265.dp,
)

internal val LocalAppPaddings = staticCompositionLocalOf { AppPaddings() }
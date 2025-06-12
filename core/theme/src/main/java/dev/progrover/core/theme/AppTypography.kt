package dev.progrover.core.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.sp

/**
 * @property titleLarge W400
 * @property titleMedium W500
 * @property bodyLarge W400
 * @property bodyMedium W400
 * @property labelLarge W500
 * @property labelMedium W500
 * @property labelMediumBold W600
 */
@Immutable
data class AppTypography(
    val materialTypography: Typography,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelMediumBold: TextStyle,
    val emoji: TextStyle,
)

internal fun appTypography(style: TextStyles): AppTypography =
    with(style) {
        AppTypography(
            materialTypography = Typography(),
            titleLarge = titleLarge,
            titleMedium = titleMedium,
            bodyLarge = bodyLarge,
            bodyMedium = bodyMedium,
            labelLarge = labelLarge,
            labelMedium = labelMedium,
            labelMediumBold = labelMediumBold,
            emoji = emoji,
        )
    }

class TextStyles {

    val titleLarge = TextStyle(
        fontWeight = W400,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    )

    val titleMedium = TextStyle(
        fontWeight = W500,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    )

    val bodyLarge = TextStyle(
        fontWeight = W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    )

    val bodyMedium = TextStyle(
        fontWeight = W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    )

    val labelLarge = TextStyle(
        fontWeight = W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    )

    val labelMedium = TextStyle(
        fontWeight = W500,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )

    val labelMediumBold = TextStyle(
        fontWeight = W600,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )

    val emoji = TextStyle(
        fontWeight = W500,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    )
}

internal val LocalAppTypography = staticCompositionLocalOf {
    appTypography(TextStyles())
}
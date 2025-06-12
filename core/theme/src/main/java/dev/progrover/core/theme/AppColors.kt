package dev.progrover.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val brightGreen: Color,
    val paleGreen: Color,
    val textMain: Color,
    val textSecondary: Color,
    val white: Color,
    val surface: Color,
    val border: Color,
    val surfaceContainer: Color,
    val containerHigh: Color,
    val outline: Color,
    val surfaceContainerHigh: Color,
) {
    companion object {
        val LightColors = AppColors(
            brightGreen = Color(0xFF2AE881),
            paleGreen = Color(0xFFD4FAE6),
            textMain = Color(0xFF1D1B20),
            textSecondary = Color(0xFF49454F),
            white = Color(0xFFFFFFFF),
            surface = Color(0xFFFEF7FF),
            border = Color(0xFFCAC4D0),
            surfaceContainer = Color(0xFFF3EDF7),
            containerHigh = Color(0xFFECE6F0),
            outline = Color(0xFF79747E),
            surfaceContainerHigh = Color(0xFFE6E0E9),
        )

        // Создал с заделом на будущее подключение темной темы
        val DarkColors = AppColors(
            brightGreen = Color(0xFF2AE881),
            paleGreen = Color(0xFFD4FAE6),
            textMain = Color(0xFF1D1B20),
            textSecondary = Color(0xFF49454F),
            white = Color(0xFFFFFFFF),
            surface = Color(0xFFFEF7FF),
            border = Color(0xFFCAC4D0),
            surfaceContainer = Color(0xFFF3EDF7),
            containerHigh = Color(0xFFECE6F0),
            outline = Color(0xFF79747E),
            surfaceContainerHigh = Color(0xFFE6E0E9),
            )
    }
}

internal val LocalAppColors = staticCompositionLocalOf {
    AppColors.LightColors
}
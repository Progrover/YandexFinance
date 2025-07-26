package dev.progrover.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val main: Color,
    val secondary: Color,
    val textMain: Color,
    val textSecondary: Color,
    val white: Color,
    val surface: Color,
    val scrim: Color,
    val border: Color,
    val surfaceContainer: Color,
    val containerHigh: Color,
    val outline: Color,
    val surfaceContainerHigh: Color,
    val error: Color,
) {
    companion object {
        val LightColors = AppColors(
            main = Color(0xFF2AE881),
            secondary = Color(0xFFD4FAE6),
            textMain = Color(0xFF1D1B20),
            textSecondary = Color(0xFF49454F),
            white = Color(0xFFFFFFFF),
            surface = Color(0xFFFEF7FF),
            border = Color(0xFFCAC4D0),
            surfaceContainer = Color(0xFFF3EDF7),
            containerHigh = Color(0xFFECE6F0),
            outline = Color(0xFF79747E),
            surfaceContainerHigh = Color(0xFFE6E0E9),
            error = Color(0xFFE46962),
            scrim = Color.Black.copy(alpha = 0.5f),
        )

        // Создал с заделом на будущее подключение темной темы
        val DarkColors = AppColors(
            main = Color(0xFF13663A),
            secondary = Color(0xFF80938A),
            textMain = Color(0xFFFFFFFF),
            textSecondary = Color(0xFFBCBBBD),
            white = Color(0xFF000000),
            surface = Color(0xFF292B2B),
            border = Color(0xFFCAC4D0),
            surfaceContainer = Color(0xFF444345),
            containerHigh = Color(0xFF1C1C1D),
            outline = Color(0xFF79747E),
            surfaceContainerHigh = Color(0xFF3A393B),
            error = Color(0xFFF5203F),
            scrim = Color.Black.copy(alpha = 0.5f),
        )
    }
}

internal fun appColors(color: String, darkMode: Boolean): AppColors {
    when (darkMode) {
        true -> {
            val mainColor = mainColorsDarkMap[color]!!
            val secondaryColor = secondaryColorsDarkMap[color]!!
            return AppColors.DarkColors.copy(
                main = mainColor,
                secondary = secondaryColor
            )
        }

        false -> {
            val mainColor = mainColorsLightMap[color]!!
            val secondaryColor = secondaryColorsLightMap[color]!!
            return AppColors.LightColors.copy(
                main = mainColor,
                secondary = secondaryColor
            )
        }
    }
}

internal val LocalAppColors = staticCompositionLocalOf {
    appColors("GREEN", false)
}

private val mainColorsDarkMap = mapOf(
    "GREEN" to Color(0xFF006F35),
    "BLUE" to Color(0xFF0B3C5F),
    "YELLOW" to Color(0xFF7F7E00),
    "PURPLE" to Color(0xFF370E5A),
    "ORANGE" to Color(0xFF6D3A00),
)

private val secondaryColorsDarkMap = mapOf(
    "GREEN" to Color(0xFF80938A),
    "BLUE" to Color(0xFF696987),
    "YELLOW" to Color(0xFF928F75),
    "PURPLE" to Color(0xFF8A8093),
    "ORANGE" to Color(0xFF938480),
)

private val mainColorsLightMap = mapOf(
    "GREEN" to Color(0xFF2AE881),
    "BLUE" to Color(0xFF2AC2E8),
    "YELLOW" to Color(0xFFDBE82A),
    "PURPLE" to Color(0xFFDB2AE8),
    "ORANGE" to Color(0xFFE87C2A),
)

private val secondaryColorsLightMap = mapOf(
    "GREEN" to Color(0xFFD4FAE6),
    "BLUE" to Color(0xFFD4EEFA),
    "YELLOW" to Color(0xFFFAF4D4),
    "PURPLE" to Color(0xFFE8D4FA),
    "ORANGE" to Color(0xFFFAE4D4),
)
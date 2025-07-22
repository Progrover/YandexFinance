package dev.progrover.core.base.utils

object SettingsOptions {

    val localeVariants =
        mapOf(
            LocaleVariant.English to "en",
            LocaleVariant.Russian to "ru"
        )

    val colorVariants =
        mapOf(
            ColorVariant.Blue to "BLUE",
            ColorVariant.Green to "GREEN",
            ColorVariant.Yellow to "YELLOW",
            ColorVariant.Orange to "ORANGE",
            ColorVariant.Purple to "PURPLE",
            )
}

enum class LocaleVariant {
    Russian,
    English
}

enum class ColorVariant {
    Green,
    Yellow,
    Blue,
    Orange,
    Purple,
}

enum class HapticsVariant {
    Silent,
    Mode_1, //todo: продумать варианты
    Mode_2
}
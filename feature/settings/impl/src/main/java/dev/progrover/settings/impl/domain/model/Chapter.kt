package dev.progrover.settings.impl.domain.model

data class Chapter(
    val route: ChapterRoute,
    val titleResId: Int,
)

enum class ChapterRoute {
    MainColor,
    Sounds,
    Haptics,
    PasswordCode,
    Sync,
    Language,
    About,
}

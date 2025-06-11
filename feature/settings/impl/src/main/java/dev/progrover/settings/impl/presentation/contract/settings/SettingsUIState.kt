package dev.progrover.settings.impl.presentation.contract.settings

import dev.progrover.core.base.presentation.mvi.UIState
import dev.progrover.settings.impl.domain.model.Chapter
import dev.progrover.settings.impl.domain.model.ChapterRoute
import dev.progrover.shmr_finance.feature.settings.impl.R

data class SettingsUIState(
    val isLoading: Boolean = false,
    val chapters: List<Chapter> = listOf(
        Chapter(
            ChapterRoute.MainColor,
            R.string.color,
        ),
        Chapter(
            ChapterRoute.Sounds,
            R.string.sounds,
        ),
        Chapter(
            ChapterRoute.Haptics,
            R.string.haptics,
        ),
        Chapter(
            ChapterRoute.PasswordCode,
            R.string.password,
        ),
        Chapter(
            ChapterRoute.Sync,
            R.string.sync,
        ),
        Chapter(
            ChapterRoute.Language,
            R.string.language,
        ),
        Chapter(
            ChapterRoute.About,
            R.string.about,
        ),
    ),
    val themeModeOn: Boolean = false,
) : UIState
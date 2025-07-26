package dev.progrover.settings.impl.presentation.viewmodel

import android.content.SharedPreferences
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.LAST_UPDATE_TIME
import dev.progrover.core.base.utils.THEME_MODE_DARK
import dev.progrover.settings.impl.domain.model.ChapterRoute
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEffect
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEvent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIState
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val prefs: Prefs
) :
    BaseViewModel<SettingsUIEvent, SettingsUIState, SettingsUIEffect>(SettingsUIState()) {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                LAST_UPDATE_TIME -> {
                    setState(
                        currentState.copy(
                            lastTimeUpdate = prefs.getString(LAST_UPDATE_TIME, "") ?: ""
                        )
                    )
                }
            }
        }

    init {
        setState(
            currentState.copy(
                lastTimeUpdate = prefs.getString(LAST_UPDATE_TIME, "") ?: "",
                themeModeOn = prefs.getBool(THEME_MODE_DARK)
            )
        )
        prefs.registerOnSharedPreferenceChangeListener(
            listener = preferenceChangeListener
        )
    }

    override fun handleUIEvent(event: SettingsUIEvent) =
        when (event) {
            is SettingsUIEvent.OnSettingsItemClick ->
                handleChapterClick(event.route)

            is SettingsUIEvent.OnThemeClick -> {
                setState(currentState.copy(themeModeOn = event.newStatus))
                prefs.putBool(THEME_MODE_DARK, event.newStatus)
            }
        }

    private fun handleChapterClick(route: ChapterRoute) {
        when (route) {
            ChapterRoute.MainColor ->
                setEffect(SettingsUIEffect.NavigateToColorsScreen)

            ChapterRoute.Haptics ->
                setEffect(SettingsUIEffect.NavigateToHapticsScreen)

            ChapterRoute.PasswordCode ->
                setEffect(SettingsUIEffect.NavigateToPinScreen)

            ChapterRoute.Sync ->
                setEffect(SettingsUIEffect.NavigateToSyncScreen)

            ChapterRoute.Language ->
                setEffect(SettingsUIEffect.NavigateToLanguageScreen)

            ChapterRoute.About ->
                setEffect(SettingsUIEffect.NavigateToAboutScreen)
        }
    }

    override fun onCleared() {
        super.onCleared()
        prefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
}
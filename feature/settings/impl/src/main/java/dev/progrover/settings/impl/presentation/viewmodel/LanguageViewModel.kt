package dev.progrover.settings.impl.presentation.viewmodel

import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.LANGUAGE
import dev.progrover.core.base.utils.SettingsOptions
import dev.progrover.settings.impl.presentation.contract.language.LanguageUIEffect
import dev.progrover.settings.impl.presentation.contract.language.LanguageUIEvent
import dev.progrover.settings.impl.presentation.contract.language.LanguageUIState
import javax.inject.Inject

class LanguageViewModel @Inject constructor(
    private val prefs: Prefs
) :
    BaseViewModel<LanguageUIEvent, LanguageUIState, LanguageUIEffect>(LanguageUIState()) {

    override fun handleUIEvent(event: LanguageUIEvent) =
        when (event) {
            LanguageUIEvent.OnBackClick ->
                setEffect(LanguageUIEffect.NavigateBack)

            is LanguageUIEvent.OnLanguageClick ->
                prefs.putString(LANGUAGE, SettingsOptions.localeVariants[event.newLanguage])
        }
}
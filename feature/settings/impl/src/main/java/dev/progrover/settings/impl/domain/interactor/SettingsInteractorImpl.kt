package dev.progrover.settings.impl.domain.interactor

import dev.progrover.settings.impl.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(
    private val repository: SettingsRepository,
) : SettingsInteractor {

}
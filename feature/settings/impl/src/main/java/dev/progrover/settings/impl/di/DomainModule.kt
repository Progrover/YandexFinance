package dev.progrover.settings.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.settings.impl.data.repository.SettingsRepositoryImpl
import dev.progrover.settings.impl.domain.interactor.SettingsInteractor
import dev.progrover.settings.impl.domain.interactor.SettingsInteractorImpl
import dev.progrover.settings.impl.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
    ): SettingsRepository =
        SettingsRepositoryImpl(
        )

    @Provides
    @Singleton
    fun provideSettingsInteractor(
        repository: SettingsRepository,
    ): SettingsInteractor =
        SettingsInteractorImpl(
            repository = repository,
        )
}
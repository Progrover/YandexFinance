package dev.progrover.settings.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.settings.impl.data.repository.SettingsRepositoryImpl
import dev.progrover.settings.impl.domain.interactor.SettingsInteractor
import dev.progrover.settings.impl.domain.interactor.SettingsInteractorImpl
import dev.progrover.settings.impl.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        prefs: Prefs,
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler
        coroutineExceptionHandler: CoroutineExceptionHandler,
        @CoroutineQualifiers.IoDispatcher
        dispatcher: CoroutineDispatcher,
    ): SettingsRepository =
        SettingsRepositoryImpl(
            prefs = prefs,
            coroutineExceptionHandler = coroutineExceptionHandler,
            dispatcher = dispatcher,
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
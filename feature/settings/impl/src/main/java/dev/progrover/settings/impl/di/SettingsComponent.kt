package dev.progrover.settings.impl.di

import dagger.Component
import dev.progrover.core.base.di.BaseDependencies

@SettingsScope
@Component(
    modules = [
        SettingsViewModelModule::class,
        SettingsNavigationModule::class,
        ColorViewModelModule::class,
        LanguageViewModelModule::class,
        HapticsViewModelModule::class,
        PinViewModelModule::class,
        AboutViewModelModule::class,
        SyncViewModelModule::class,
    ],
    dependencies = [BaseDependencies::class]
)
internal interface SettingsComponent {
    fun getViewModelFactory(): ViewModelFactory
}



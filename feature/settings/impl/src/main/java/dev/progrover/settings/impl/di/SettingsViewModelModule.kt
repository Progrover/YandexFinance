package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.SettingsViewModel

@Module
internal interface SettingsViewModelModule {

    @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}
package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.HapticsViewModel

@Module
internal interface HapticsViewModelModule {

    @[Binds IntoMap ViewModelKey(HapticsViewModel::class)]
    fun bindHapticsViewModel(viewModel: HapticsViewModel): ViewModel
}
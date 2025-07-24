package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.PinViewModel

@Module
internal interface PinViewModelModule {

    @[Binds IntoMap ViewModelKey(PinViewModel::class)]
    fun bindPinViewModel(viewModel: PinViewModel): ViewModel
}
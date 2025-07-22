package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.ColorViewModel

@Module
internal interface ColorViewModelModule {

    @[Binds IntoMap ViewModelKey(ColorViewModel::class)]
    fun bindColorViewModel(viewModel: ColorViewModel): ViewModel
}
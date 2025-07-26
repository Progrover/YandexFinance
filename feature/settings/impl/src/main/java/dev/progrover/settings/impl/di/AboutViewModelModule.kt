package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.AboutViewModel

@Module
internal interface AboutViewModelModule {

    @[Binds IntoMap ViewModelKey(AboutViewModel::class)]
    fun bindAboutViewModel(viewModel: AboutViewModel): ViewModel
}
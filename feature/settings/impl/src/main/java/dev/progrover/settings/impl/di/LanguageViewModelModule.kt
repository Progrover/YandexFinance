package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.LanguageViewModel

@Module
internal interface LanguageViewModelModule {

    @[Binds IntoMap ViewModelKey(LanguageViewModel::class)]
    fun bindLanguageViewModel(viewModel: LanguageViewModel): ViewModel
}
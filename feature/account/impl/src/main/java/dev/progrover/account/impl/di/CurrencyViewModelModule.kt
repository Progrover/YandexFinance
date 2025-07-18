package dev.progrover.account.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.account.impl.presentation.viewmodel.CurrencyViewModel

@Module
internal interface CurrencyViewModelModule {

    @[Binds IntoMap ViewModelKey(CurrencyViewModel::class)]
    fun bindArticlesViewModel(viewModel: CurrencyViewModel): ViewModel
}
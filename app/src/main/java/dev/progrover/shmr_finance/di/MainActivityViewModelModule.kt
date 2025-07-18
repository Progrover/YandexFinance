package dev.progrover.shmr_finance.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.shmr_finance.viewmodel.MainActivityViewModel

@Module
internal interface MainActivityViewModelModule {

    @[Binds IntoMap ViewModelKey(MainActivityViewModel::class)]
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}
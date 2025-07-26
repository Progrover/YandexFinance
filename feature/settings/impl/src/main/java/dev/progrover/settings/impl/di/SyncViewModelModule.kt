package dev.progrover.settings.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.settings.impl.presentation.viewmodel.SyncViewModel

@Module
internal interface SyncViewModelModule {

    @[Binds IntoMap ViewModelKey(SyncViewModel::class)]
    fun bindSyncViewModel(viewModel: SyncViewModel): ViewModel
}
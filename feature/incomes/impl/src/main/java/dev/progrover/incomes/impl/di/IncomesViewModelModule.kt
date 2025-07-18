package dev.progrover.incomes.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.incomes.impl.presentation.viewmodel.IncomesViewModel

@Module
internal interface IncomesViewModelModule {

    @[Binds IntoMap ViewModelKey(IncomesViewModel::class)]
    fun bindIncomesViewModel(viewModel: IncomesViewModel): ViewModel
}
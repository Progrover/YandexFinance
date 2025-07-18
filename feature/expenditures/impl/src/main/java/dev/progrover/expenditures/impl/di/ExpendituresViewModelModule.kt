package dev.progrover.expenditures.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.expenditures.impl.presentation.viewmodel.ExpendituresViewModel

@Module
internal interface ExpendituresViewModelModule {

    @[Binds IntoMap ViewModelKey(ExpendituresViewModel::class)]
    fun bindExpendituresViewModel(viewModel: ExpendituresViewModel): ViewModel
}
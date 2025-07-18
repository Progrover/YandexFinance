package dev.progrover.account.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.account.impl.presentation.viewmodel.AccountViewModel

@Module
internal interface AccountViewModelModule {

    @[Binds IntoMap ViewModelKey(AccountViewModel::class)]
    fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel
}
package dev.progrover.account.impl.di

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.AssistedFactory
import dev.progrover.account.impl.presentation.viewmodel.BalanceNameViewModel

@AssistedFactory
interface BalanceNameViewModelAssistedFactory {
    fun create(savedStateHandle: SavedStateHandle): BalanceNameViewModel
}
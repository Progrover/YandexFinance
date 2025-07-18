package dev.progrover.history.impl.di

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.AssistedFactory
import dev.progrover.history.impl.presentation.viewmodel.HistoryViewModel

@AssistedFactory
interface HistoryViewModelAssistedFactory {
    fun create(savedStateHandle: SavedStateHandle): HistoryViewModel
}
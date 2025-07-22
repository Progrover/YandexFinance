package dev.progrover.history.impl.di

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.AssistedFactory
import dev.progrover.history.impl.presentation.viewmodel.AnalysisViewModel

@AssistedFactory
interface AnalysisViewModelAssistedFactory {
    fun create(savedStateHandle: SavedStateHandle): AnalysisViewModel
}
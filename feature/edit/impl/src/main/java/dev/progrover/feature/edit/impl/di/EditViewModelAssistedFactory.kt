package dev.progrover.feature.edit.impl.di

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.AssistedFactory
import dev.progrover.feature.edit.impl.presentation.viewmodel.EditViewModel

@AssistedFactory
interface EditViewModelAssistedFactory {
    fun create(savedStateHandle: SavedStateHandle): EditViewModel
}
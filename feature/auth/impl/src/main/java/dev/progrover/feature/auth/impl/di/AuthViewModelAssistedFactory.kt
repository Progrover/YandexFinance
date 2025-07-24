package dev.progrover.feature.auth.impl.di

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.AssistedFactory
import dev.progrover.feature.auth.impl.presentation.viewmodel.AuthViewModel

@AssistedFactory
interface AuthViewModelAssistedFactory {
    fun create(savedStateHandle: SavedStateHandle): AuthViewModel
}
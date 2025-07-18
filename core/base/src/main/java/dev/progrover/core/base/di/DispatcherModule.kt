package dev.progrover.core.base.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatcherModule {

    @CoroutineQualifiers.DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher =
        Dispatchers.Default

    @CoroutineQualifiers.IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    @CoroutineQualifiers.MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher =
        Dispatchers.Main
}
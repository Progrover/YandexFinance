package dev.progrover.core.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
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
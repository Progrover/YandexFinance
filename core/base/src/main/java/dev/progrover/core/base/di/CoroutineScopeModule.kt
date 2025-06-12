package dev.progrover.core.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutineScopeModule {

    @Singleton
    @Provides
    @CoroutineQualifiers.ApplicationScope
    fun provideCoroutineScope(
        @CoroutineQualifiers.DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler coroutineExceptionHandler: CoroutineExceptionHandler,
    ): CoroutineScope =
        CoroutineScope(SupervisorJob() + defaultDispatcher + coroutineExceptionHandler)

    @Singleton
    @Provides
    @CoroutineQualifiers.IOCoroutineScope
    fun provideUICoroutineScope(
        @CoroutineQualifiers.IoDispatcher ioDispatcher: CoroutineDispatcher,
        @CoroutineQualifiers.DefaultCoroutineExceptionHandler coroutineExceptionHandler: CoroutineExceptionHandler,
    ): CoroutineScope =
        CoroutineScope(SupervisorJob() + ioDispatcher + coroutineExceptionHandler)

    @Singleton
    @Provides
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable, "ошибка в DefaultCoroutineExceptionHandler")
        }
}
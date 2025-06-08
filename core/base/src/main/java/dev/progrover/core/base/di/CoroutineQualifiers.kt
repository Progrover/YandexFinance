package dev.progrover.core.base.di

import javax.inject.Qualifier

interface CoroutineQualifiers {

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class DefaultCoroutineExceptionHandler

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class ApplicationScope

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IOCoroutineScope
}
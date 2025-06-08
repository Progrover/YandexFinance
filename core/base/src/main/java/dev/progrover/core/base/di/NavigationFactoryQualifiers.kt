package dev.progrover.core.base.di

import javax.inject.Qualifier

interface NavigationFactoryQualifiers {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainActivity
}
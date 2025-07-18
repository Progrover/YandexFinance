package dev.progrover.shmr_finance.di

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class WorkerModule {

    @Binds
    @Singleton
    abstract fun bindWorkerFactory(
        customWorkerFactory: CustomWorkerFactory
    ): WorkerFactory

    //todo: предоставить реализации воркеров по необходимости
}

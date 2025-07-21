package dev.progrover.shmr_finance.di

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.shmr_finance.workmanager.StartupWorker
import dev.progrover.shmr_finance.workmanager.StartupWorkerFactoryImpl
import dev.progrover.shmr_finance.workmanager.SyncWorker
import dev.progrover.shmr_finance.workmanager.SyncWorkerFactoryImpl
import javax.inject.Singleton

@Module
interface WorkerModule {

    @Binds
    @Singleton
    fun bindWorkerFactory(
        customWorkerFactory: CustomWorkerFactory
    ): WorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(SyncWorker::class)
    fun bindSyncWorkerFactory(factory: SyncWorkerFactoryImpl): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(StartupWorker::class)
    fun bindStartupWorkerFactory(factory: StartupWorkerFactoryImpl): ChildWorkerFactory
}

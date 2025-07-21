package dev.progrover.shmr_finance.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards ChildWorkerFactory>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerClass = try {
            Class.forName(workerClassName) as Class<out ListenableWorker>
        } catch (e: Exception) {
            return null
        }
        val factory = workerFactories[workerClass] ?: return null
        return factory.create(appContext, workerParameters)
    }
}
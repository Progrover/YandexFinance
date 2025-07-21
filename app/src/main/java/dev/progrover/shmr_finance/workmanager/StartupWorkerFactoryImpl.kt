package dev.progrover.shmr_finance.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import dev.progrover.shmr_finance.di.ChildWorkerFactory
import javax.inject.Inject

class StartupWorkerFactoryImpl @Inject constructor(
    private val factory: StartupWorker.Factory
) : ChildWorkerFactory {
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return factory.create(appContext, params)
    }
}

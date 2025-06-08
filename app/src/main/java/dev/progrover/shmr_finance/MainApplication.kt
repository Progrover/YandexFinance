package dev.progrover.shmr_finance

import TimberReleaseTree
import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import dev.progrover.core.uicommon.utils.ImageRequestDefaults
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication :
    Application(),
    DefaultLifecycleObserver,
    Configuration.Provider,
    ImageLoaderFactory {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super<Application>.onCreate()

        val isDebugBuild = BuildConfig.DEBUG
        val timberTree = when (isDebugBuild) {
            true -> Timber.DebugTree()
            false -> TimberReleaseTree()
        }
        Timber.plant(timberTree)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun newImageLoader(): ImageLoader {
        val builder = ImageLoader.Builder(this)
        return builder
            .diskCachePolicy(ImageRequestDefaults.cachePolicy)
            .memoryCachePolicy(ImageRequestDefaults.cachePolicy)
            .networkCachePolicy(ImageRequestDefaults.cachePolicy)
            .build()
    }
}
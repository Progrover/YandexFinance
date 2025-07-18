package dev.progrover.shmr_finance

import TimberReleaseTree
import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import coil.ImageLoader
import coil.ImageLoaderFactory
import dev.progrover.core.base.di.BaseComponent
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.core.base.di.DaggerBaseComponent
import dev.progrover.core.uicommon.utils.ImageRequestDefaults
import dev.progrover.shmr_finance.di.ApplicationComponent
import dev.progrover.shmr_finance.di.ApplicationComponentProvider
import dev.progrover.shmr_finance.di.CustomWorkerFactory
import dev.progrover.shmr_finance.di.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

class MainApplication :
    Application(),
    DefaultLifecycleObserver,
    Configuration.Provider,
    ImageLoaderFactory,
    BaseComponentProvider,
    ApplicationComponentProvider {

    private lateinit var appComponent: ApplicationComponent

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override fun onCreate() {
        super<Application>.onCreate()

        appComponent = DaggerApplicationComponent.factory()
            .create(this, _baseComponent)

        appComponent.inject(this)

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

    private val _baseComponent: BaseComponent by lazy {
        DaggerBaseComponent.factory().create(this, this)
    }

    override fun getBaseComponent(): BaseComponent =
        _baseComponent

    override fun getApplicationComponent(): ApplicationComponent =
        appComponent
}
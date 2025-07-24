package dev.progrover.shmr_finance

import TimberReleaseTree
import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.ImageLoader
import coil.ImageLoaderFactory
import dev.progrover.account.impl.di.AccountComponent
import dev.progrover.account.impl.di.AccountComponentProvider
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.BaseComponent
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.core.base.di.DaggerBaseComponent
import dev.progrover.core.base.utils.LANGUAGE
import dev.progrover.core.base.utils.LocaleVariant
import dev.progrover.core.base.utils.SYNC_TIME_HOURS
import dev.progrover.core.base.utils.SettingsOptions
import dev.progrover.core.uicommon.utils.ImageRequestDefaults
import dev.progrover.shmr_finance.di.ApplicationComponent
import dev.progrover.shmr_finance.di.ApplicationComponentProvider
import dev.progrover.shmr_finance.di.CustomWorkerFactory
import dev.progrover.shmr_finance.di.DaggerApplicationComponent
import dev.progrover.shmr_finance.network.NetworkMonitor
import dev.progrover.shmr_finance.workmanager.StartupWorker
import dev.progrover.shmr_finance.workmanager.SyncWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainApplication :
    Application(),
    DefaultLifecycleObserver,
    Configuration.Provider,
    ImageLoaderFactory,
    BaseComponentProvider,
    AccountComponentProvider,
    ApplicationComponentProvider {

    private lateinit var appComponent: ApplicationComponent

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    @Inject
    lateinit var prefs: Prefs

    lateinit var networkMonitor: NetworkMonitor

    var splashAnimationEnd = false

    var pinCodeShown = false

    private val prefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == SYNC_TIME_HOURS) {
            updatePeriodicWorker(prefs.getInt(SYNC_TIME_HOURS, 4))
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super<Application>.onCreate()

        appComponent = DaggerApplicationComponent.factory()
            .create(this, _baseComponent)

        appComponent.inject(this)

        if (prefs.getString(LANGUAGE, null) == null) {
            prefs.putString(LANGUAGE, SettingsOptions.localeVariants[LocaleVariant.Russian])
        }

        val isFirstLaunch = prefs.getBool("is_first_launch", true)
        if (isFirstLaunch) onceRequest()

        networkMonitor = NetworkMonitor(this) {
            Timber.d("NetworkMonitor toggle")
            WorkManager.getInstance(this)
                .enqueue(OneTimeWorkRequestBuilder<SyncWorker>().build())
        }
        networkMonitor.start()

        val isDebugBuild = BuildConfig.DEBUG
        val timberTree = when (isDebugBuild) {
            true -> Timber.DebugTree()
            false -> TimberReleaseTree()
        }
        Timber.plant(timberTree)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        setPeriodicWorker()

        prefs.registerOnSharedPreferenceChangeListener(prefsListener)
    }

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

    private fun setPeriodicWorker() {
        val immediateWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()

        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            prefs.getInt(SYNC_TIME_HOURS, 4).toLong(), TimeUnit.HOURS
        ).build()
        val workManager = WorkManager.getInstance(this)

        workManager.enqueue(immediateWorkRequest)

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun updatePeriodicWorker(newInterval: Int) {
        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            newInterval.toLong(), TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
        Timber.d("MainApplication: SyncWorker interval updated")
    }

    private fun onceRequest() {
        val workRequest = OneTimeWorkRequestBuilder<StartupWorker>().build()
        WorkManager.getInstance(
            this
        ).enqueue(workRequest)
        prefs.putBool("is_first_launch", false)
    }

    override fun getAccountComponent(): AccountComponent =
        appComponent.getAccountComponent()

    internal fun setAnimationEnd() {
        splashAnimationEnd = true
    }

    internal fun setPinCodeShown() {
        pinCodeShown = true
    }
}
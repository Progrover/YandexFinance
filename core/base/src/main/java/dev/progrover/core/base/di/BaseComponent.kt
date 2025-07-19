package dev.progrover.core.base.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreUtilsModule::class,
        CoroutineScopeModule::class,
        DataModule::class,
        DispatcherModule::class,
        NetworkModule::class,
        LocalDataModule::class,
    ]
)
interface BaseComponent : BaseDependencies {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context,
        ): BaseComponent
    }
}
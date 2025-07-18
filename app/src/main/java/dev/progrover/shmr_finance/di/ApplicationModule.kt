package dev.progrover.shmr_finance.di

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.Module
import dagger.Provides
import dev.progrover.core.uicommon.utils.ImageRequestDefaults
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideImageLoaderFactory(context: Context): ImageLoaderFactory =
        ImageLoaderFactory {
            ImageLoader.Builder(context)
                .diskCachePolicy(ImageRequestDefaults.cachePolicy)
                .memoryCachePolicy(ImageRequestDefaults.cachePolicy)
                .networkCachePolicy(ImageRequestDefaults.cachePolicy)
                .build()
        }
}
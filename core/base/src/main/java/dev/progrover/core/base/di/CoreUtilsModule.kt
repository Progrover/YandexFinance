package dev.progrover.core.base.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dev.progrover.core.base.utils.JsonConverter
import javax.inject.Singleton

@Module
class CoreUtilsModule {

    @Provides
    @Singleton
    fun provideJsonConverter(moshi: Moshi): JsonConverter =
        JsonConverter(moshi = moshi)
}
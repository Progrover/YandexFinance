package dev.progrover.core.base.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.data.storage.SharedPrefsImpl
import dev.progrover.core.base.model.TransactionsUpdater
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideContentResolver(
        context: Context,
    ): ContentResolver =
        context.contentResolver

    @Provides
    @Singleton
    fun provideSharedPrefs(
        application: Application,
        moshi: Moshi
    ): Prefs =
        SharedPrefsImpl(
            application = application,
            prefsName = "cookiePrefs",
            moshi = moshi
        )

    @Provides
    @Singleton
    fun providesTransactionUpdater() = TransactionsUpdater
}
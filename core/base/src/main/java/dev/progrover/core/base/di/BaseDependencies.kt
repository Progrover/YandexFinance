package dev.progrover.core.base.di

import android.content.ContentResolver
import com.squareup.moshi.Moshi
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.interceptor.BaseInterceptor
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.utils.JsonConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

interface BaseDependencies {
    fun jsonConverter(): JsonConverter

    @CoroutineQualifiers.ApplicationScope
    fun applicationScope(): CoroutineScope

    @CoroutineQualifiers.IOCoroutineScope
    fun ioScope(): CoroutineScope

    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    fun coroutineExceptionHandler(): CoroutineExceptionHandler

    fun contentResolver(): ContentResolver

    fun prefs(): Prefs

    fun transactionsUpdater(): TransactionsUpdater

    @CoroutineQualifiers.DefaultDispatcher
    fun defaultDispatcher(): CoroutineDispatcher

    @CoroutineQualifiers.IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher

    @CoroutineQualifiers.MainDispatcher
    fun mainDispatcher(): CoroutineDispatcher

    fun moshi(): Moshi

    fun moshiConverterFactory(): Converter.Factory

    fun retrofit(): Retrofit

    fun okhttp(): OkHttpClient

    fun baseInterceptor(): BaseInterceptor

    fun transactionsApi(): TransactionsApi
}
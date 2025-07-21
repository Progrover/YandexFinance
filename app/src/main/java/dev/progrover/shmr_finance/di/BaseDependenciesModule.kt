package dev.progrover.shmr_finance.di

import android.content.ContentResolver
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.interceptor.BaseInterceptor
import dev.progrover.core.base.data.local.TransactionDao
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.utils.JsonConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
object BaseDependenciesModule {
    @Provides
    fun jsonConverter(deps: BaseDependencies): JsonConverter = deps.jsonConverter()

    @Provides
    @CoroutineQualifiers.ApplicationScope
    fun applicationScope(deps: BaseDependencies): CoroutineScope = deps.applicationScope()

    @Provides
    @CoroutineQualifiers.IOCoroutineScope
    fun ioScope(deps: BaseDependencies): CoroutineScope = deps.ioScope()

    @Provides
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    fun coroutineExceptionHandler(deps: BaseDependencies): CoroutineExceptionHandler =
        deps.coroutineExceptionHandler()

    @Provides
    fun contentResolver(deps: BaseDependencies): ContentResolver = deps.contentResolver()

    @Provides
    fun prefs(deps: BaseDependencies): Prefs = deps.prefs()

    @Provides
    fun transactionsUpdater(deps: BaseDependencies): TransactionsUpdater =
        deps.transactionsUpdater()

    @Provides
    @CoroutineQualifiers.DefaultDispatcher
    fun defaultDispatcher(deps: BaseDependencies): CoroutineDispatcher = deps.defaultDispatcher()

    @Provides
    @CoroutineQualifiers.IoDispatcher
    fun ioDispatcher(deps: BaseDependencies): CoroutineDispatcher = deps.ioDispatcher()

    @Provides
    @CoroutineQualifiers.MainDispatcher
    fun mainDispatcher(deps: BaseDependencies): CoroutineDispatcher = deps.mainDispatcher()

    @Provides
    fun moshi(deps: BaseDependencies): Moshi = deps.moshi()

    @Provides
    fun moshiConverterFactory(deps: BaseDependencies): Converter.Factory =
        deps.moshiConverterFactory()

    @Provides
    fun retrofit(deps: BaseDependencies): Retrofit = deps.retrofit()

    @Provides
    fun okhttp(deps: BaseDependencies): OkHttpClient = deps.okhttp()

    @Provides
    fun baseInterceptor(deps: BaseDependencies): BaseInterceptor = deps.baseInterceptor()

    @Provides
    fun transactionsApi(deps: BaseDependencies): TransactionsApi = deps.transactionsApi()

    @Provides
    fun transactionDao(deps: BaseDependencies): TransactionDao = deps.transactionDao()

    @Provides
    fun localTransactionProvider(deps: BaseDependencies): LocalTransactionProvider =
        deps.localTransactionProvider()

    @Provides
    fun localArticleProvider(deps: BaseDependencies): LocalArticleProvider =
        deps.localArticleProvider()

    @Provides
    fun localAccountProvider(deps: BaseDependencies): LocalAccountProvider =
        deps.localAccountProvider()
}
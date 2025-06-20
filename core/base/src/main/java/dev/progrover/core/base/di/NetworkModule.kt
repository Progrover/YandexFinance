package dev.progrover.core.base.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.core.base.data.api.TransactionsApi
import dev.progrover.core.base.data.interceptor.BaseInterceptor
import dev.progrover.core.base.data.interceptor.BaseInterceptorImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        val moshiBuilder = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())

        return moshiBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        baseInterceptor: BaseInterceptor,
    ): OkHttpClient =
        buildOkHttpClient(
            baseInterceptor = baseInterceptor,
        )

    @Provides
    @Singleton
    fun provideBaseInterceptor(): BaseInterceptor =
        BaseInterceptorImpl()

    private fun buildOkHttpClient(
        baseInterceptor: BaseInterceptor,
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(baseInterceptor)
            .addInterceptor(logging)
            .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesTransactionsApi(
        retrofit: Retrofit
    ): TransactionsApi =
        retrofit.create(TransactionsApi::class.java)

    companion object {

        private const val READ_TIMEOUT_IN_SECONDS = 30L
        private const val CONNECTION_TIMEOUT_IN_SECONDS = 30L
    }
}
package dev.progrover.account.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.account.impl.data.api.AccountApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesAccountApi(
        retrofit: Retrofit
    ): AccountApi =
        retrofit.create(AccountApi::class.java)
}
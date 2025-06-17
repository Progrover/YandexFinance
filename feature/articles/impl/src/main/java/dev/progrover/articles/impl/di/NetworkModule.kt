package dev.progrover.articles.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.articles.impl.data.api.ArticlesApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesArticlesApi(
        retrofit: Retrofit
    ): ArticlesApi =
        retrofit.create(ArticlesApi::class.java)
}
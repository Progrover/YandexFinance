package dev.progrover.expenditures.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.expenditures.impl.data.api.ExpendituresApi
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapperImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesExpendituresApi(
        retrofit: Retrofit
    ): ExpendituresApi =
        retrofit.create(ExpendituresApi::class.java)

    @Provides
    @Singleton
    fun providesExpendituresDTOMapper(
    ): ExpendituresDTOMapper =
        ExpendituresDTOMapperImpl()
}
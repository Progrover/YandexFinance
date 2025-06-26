package dev.progrover.incomes.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.incomes.impl.data.mapper.IncomesDTOMapper
import dev.progrover.incomes.impl.data.mapper.IncomesDTOMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesIncomesDTOMapper(): IncomesDTOMapper =
        IncomesDTOMapperImpl()
}
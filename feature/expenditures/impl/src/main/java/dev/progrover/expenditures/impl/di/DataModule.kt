package dev.progrover.expenditures.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesExpendituresDTOMapper(
    ): ExpendituresDTOMapper =
        ExpendituresDTOMapperImpl()
}
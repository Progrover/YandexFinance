package dev.progrover.history.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.history.impl.data.mapper.HistoryDTOMapper
import dev.progrover.history.impl.data.mapper.HistoryDTOMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesHistoryDTOMapper(
    ): HistoryDTOMapper =
        HistoryDTOMapperImpl()
}
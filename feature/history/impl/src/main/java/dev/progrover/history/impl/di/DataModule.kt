package dev.progrover.history.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.history.impl.data.mapper.HistoryDTOMapper
import dev.progrover.history.impl.data.mapper.HistoryDTOMapperImpl

@Module
class DataModule {

    @[HistoryScope Provides]
    fun providesHistoryDTOMapper(): HistoryDTOMapper =
        HistoryDTOMapperImpl()
}
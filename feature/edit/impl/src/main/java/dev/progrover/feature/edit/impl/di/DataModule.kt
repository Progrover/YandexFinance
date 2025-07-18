package dev.progrover.feature.edit.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.feature.edit.impl.data.mapper.EditDTOMapper
import dev.progrover.feature.edit.impl.data.mapper.EditDTOMapperImpl

@Module
class DataModule {

    @[EditScope Provides]
    fun providesEditDTOMapper(
    ): EditDTOMapper =
        EditDTOMapperImpl()
}
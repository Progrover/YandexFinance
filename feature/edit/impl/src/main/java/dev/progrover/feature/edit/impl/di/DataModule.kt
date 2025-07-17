package dev.progrover.feature.edit.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.progrover.feature.edit.impl.data.mapper.EditDTOMapper
import dev.progrover.feature.edit.impl.data.mapper.EditDTOMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesEditDTOMapper(
    ): EditDTOMapper =
        EditDTOMapperImpl()
}
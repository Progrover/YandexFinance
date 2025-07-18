package dev.progrover.expenditures.impl.di

import dagger.Module
import dagger.Provides
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapper
import dev.progrover.expenditures.impl.data.mapper.ExpendituresDTOMapperImpl

@Module
class DataModule {

    @Provides
    @ExpendituresScope
    fun providesExpendituresDTOMapper(): ExpendituresDTOMapper =
        ExpendituresDTOMapperImpl()
}
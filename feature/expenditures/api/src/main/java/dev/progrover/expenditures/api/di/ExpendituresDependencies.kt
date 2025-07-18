package dev.progrover.expenditures.api.di

import dev.progrover.expenditures.api.domain.interactor.ExpendituresInteractor

interface ExpendituresDependencies {
    fun expendituresInteractor(): ExpendituresInteractor
}
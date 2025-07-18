package dev.progrover.incomes.api.di

import dev.progrover.incomes.api.domain.interactor.IncomesInteractor

interface IncomesDependencies {
    fun incomesInteractor(): IncomesInteractor
}
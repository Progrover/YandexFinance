package dev.progrover.incomes.impl.domain.interactor

import dev.progrover.incomes.impl.domain.repository.IncomesRepository
import javax.inject.Inject

class IncomesInteractorImpl @Inject constructor(
    private val repository: IncomesRepository,
) : IncomesInteractor {

}
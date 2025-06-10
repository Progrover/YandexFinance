package dev.progrover.account.impl.domain.interactor

import dev.progrover.account.impl.domain.repository.AccountRepository
import javax.inject.Inject

class AccountInteractorImpl @Inject constructor(
    private val repository: AccountRepository,
) : AccountInteractor {

}
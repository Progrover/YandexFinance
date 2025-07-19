package dev.progrover.account.api.di

import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.account.api.domain.interactor.AccountInteractor

interface AccountDependencies {
    fun propertiesProvider(): AccountPropertiesProvider
    fun accountInteractor() : AccountInteractor
}
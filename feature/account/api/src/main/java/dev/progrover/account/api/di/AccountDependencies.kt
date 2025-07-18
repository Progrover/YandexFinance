package dev.progrover.account.api.di

import dev.progrover.account.api.domain.AccountPropertiesProvider

interface AccountDependencies {
    fun propertiesProvider(): AccountPropertiesProvider
}
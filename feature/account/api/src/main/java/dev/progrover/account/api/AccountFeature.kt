package dev.progrover.account.api

import androidx.navigation.NavController
import dev.progrover.core.base.utils.customNavigate

/**
 * Класс, содержащий варианты навигации к экранам account feature
 */
object AccountFeature {

    const val ROUTE_NAME = "accountFeature"
    const val ACCOUNT_SCREEN = "accountScreenRoute"
    const val CURRENCY_SCREEN = "currencyScreenRoute"

    fun openAccountScreen(navController: NavController) =
        navController.customNavigate(ACCOUNT_SCREEN)

    fun openCurrencyPickScreen(navController: NavController) =
        navController.customNavigate(CURRENCY_SCREEN)
}

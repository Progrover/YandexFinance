package dev.progrover.core.base.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
/**
 * Интерфейс для связывания всех вложенных графов навигации
 */
interface NavigationFactory {

    fun create(builder: NavGraphBuilder, navController: NavHostController)
}
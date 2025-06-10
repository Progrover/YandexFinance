package dev.progrover.shmr_finance.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.progrover.account.api.AccountFeature
import dev.progrover.articles.api.ArticlesFeature
import dev.progrover.expenditures.api.ExpendituresFeature
import dev.progrover.incomes.api.IncomesFeature
import dev.progrover.settings.api.SettingsFeature
import dev.progrover.shmr_finance.contract.MainUIState
import dev.progrover.shmr_finance.navigation.model.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    uiState: MainUIState,
    navController: NavController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigationBarContent(
        isBottomBarVisible = navBackStackEntry.isBottomBarVisible(uiState.isBottomNavigationBarVisible),
        currentDestination = navBackStackEntry?.destination,
        onClick = { item ->
            when (item) {
                BottomNavigationItem.AccountScreen ->
                    AccountFeature.openAccountScreen(navController)

                BottomNavigationItem.ArticlesScreen ->
                    ArticlesFeature.openArticlesScreen(navController)

                BottomNavigationItem.ExpendituresScreen ->
                    ExpendituresFeature.openExpendituresScreen(navController)

                BottomNavigationItem.IncomesScreen ->
                    IncomesFeature.openIncomesScreen(navController)

                BottomNavigationItem.SettingsScreen ->
                    SettingsFeature.openSettingsScreen(navController)
            }
        }
    )
}

/**
 * Функция для управления видимостью навигационного меню
 */
private fun NavBackStackEntry?.isBottomBarVisible(isBottomBarVisible: Boolean): Boolean {
    val screensWhereBottomBarVisible = listOf(
        AccountFeature.ACCOUNT_SCREEN,
        ArticlesFeature.ARTICLES_SCREEN,
        ExpendituresFeature.EXPENDITURES_SCREEN,
        SettingsFeature.SETTINGS_SCREEN,
        IncomesFeature.INCOMES_SCREEN,
    )
    return when (isBottomBarVisible) {
        true -> this?.destination?.route in screensWhereBottomBarVisible
        false -> false
    }
}
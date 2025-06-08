package dev.progrover.shmr_finance.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.progrover.incomes.api.IncomesFeature
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
                    Unit

                BottomNavigationItem.ArticlesScreen ->
                    Unit

                BottomNavigationItem.ExpendituresScreen ->
                    Unit

                BottomNavigationItem.IncomesScreen ->
                    Unit

                BottomNavigationItem.SettingsScreen ->
                    Unit
            }
        }
    )
}

/**
 * Функция для управления видимостью навигационного меню
 */
private fun NavBackStackEntry?.isBottomBarVisible(isBottomBarVisible: Boolean): Boolean {
    val screensWhereBottomBarVisible = listOf(
        IncomesFeature.ROUTE_NAME,
    )
    return when (isBottomBarVisible) {
        true -> this?.destination?.route in screensWhereBottomBarVisible
        false -> false
    }
}
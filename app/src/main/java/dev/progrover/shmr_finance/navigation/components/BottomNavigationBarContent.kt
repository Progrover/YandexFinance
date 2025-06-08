package dev.progrover.shmr_finance.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dev.progrover.core.theme.AppTheme
import dev.progrover.shmr_finance.navigation.model.BottomNavigationItem

@Composable
fun BottomNavigationBarContent(
    isBottomBarVisible: Boolean,
    currentDestination: NavDestination?,
    onClick: (BottomNavigationItem) -> Unit,
) {

    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {

        Surface(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .background(AppTheme.colors.surface)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.sizes.size80)
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.ExpendituresScreen,
                        isSelected =
                            currentDestination?.hierarchy
                                ?.any { it.route == BottomNavigationItem.IncomesScreen.route } == true,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.IncomesScreen,
                        isSelected = false,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.AccountScreen,
                        isSelected = false,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.ArticlesScreen,
                        isSelected = false,
                        onClick = onClick,
                    )

                    BottomBarItem(
                        modifier = Modifier
                            .height(AppTheme.sizes.size45),
                        item = BottomNavigationItem.SettingsScreen,
                        isSelected = false,
                        onClick = onClick,
                    )
                }
            }
        }
    }
}
package dev.progrover.shmr_finance.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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

            Row(
                modifier = Modifier
                    .background(AppTheme.colors.surfaceContainer)
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.paddings.extraSmall)
                    .height(AppTheme.sizes.size80)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                BottomBarItem(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.paddings.padding12,
                            bottom = AppTheme.paddings.padding16,
                        ),
                    item = BottomNavigationItem.ExpendituresScreen,
                    isSelected =
                        currentDestination?.hierarchy
                            ?.any { it.route == BottomNavigationItem.ExpendituresScreen.route } == true,
                    onClick = onClick,
                )

                BottomBarItem(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.paddings.padding12,
                            bottom = AppTheme.paddings.padding16,
                        ),
                    item = BottomNavigationItem.IncomesScreen,
                    isSelected = currentDestination?.hierarchy
                        ?.any { it.route == BottomNavigationItem.IncomesScreen.route } == true,
                    onClick = onClick,
                )

                BottomBarItem(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.paddings.padding12,
                            bottom = AppTheme.paddings.padding16,
                        ),
                    item = BottomNavigationItem.AccountScreen,
                    isSelected = currentDestination?.hierarchy
                        ?.any { it.route == BottomNavigationItem.AccountScreen.route } == true,
                    onClick = onClick,
                )

                BottomBarItem(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.paddings.padding12,
                            bottom = AppTheme.paddings.padding16,
                        ),
                    item = BottomNavigationItem.ArticlesScreen,
                    isSelected = currentDestination?.hierarchy
                        ?.any { it.route == BottomNavigationItem.ArticlesScreen.route } == true,
                    onClick = onClick,
                )

                BottomBarItem(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.paddings.padding12,
                            bottom = AppTheme.paddings.padding16,
                        ),
                    item = BottomNavigationItem.SettingsScreen,
                    isSelected = currentDestination?.hierarchy
                        ?.any { it.route == BottomNavigationItem.SettingsScreen.route } == true,
                    onClick = onClick,
                )
            }
        }
    }
}
package dev.progrover.shmr_finance.navigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.shmr_finance.navigation.model.BottomNavigationItem

@Composable
fun RowScope.BottomBarItem(
    modifier: Modifier,
    item: BottomNavigationItem,
    isSelected: Boolean,
    onClick: (BottomNavigationItem) -> Unit,
    selectedColor: Color = AppTheme.colors.brightGreen,
    unselectedColor: Color = AppTheme.colors.textSecondary,
) {

    NavigationBarItem(
        modifier = modifier,
        icon = {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(
                        when (isSelected) {
                            true -> AppTheme.colors.paleGreen
                            false -> Color.Transparent
                        }
                    ),
            ) {

                Image(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.paddings.padding20,
                            vertical = AppTheme.paddings.padding4,
                        )
                        .size(AppTheme.sizes.size24)
                        .align(Alignment.Center),
                    painter = painterResource(item.iconResId),
                    contentDescription = null,
                    colorFilter = when (isSelected) {
                        true -> ColorFilter.tint(selectedColor)
                        false -> ColorFilter.tint(unselectedColor)
                    }
                )
            }
        },
        label = {

            Text(
                modifier = Modifier,
                text = stringResource(id = item.caption),
                style = when (isSelected) {
                    true -> AppTheme.typography.labelMediumBold
                    false -> AppTheme.typography.labelMedium
                },
                color = when (isSelected) {
                    true -> AppTheme.colors.textMain
                    false -> AppTheme.colors.textSecondary
                },
            )
        },
        interactionSource = remember { MutableInteractionSource() },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedColor,
            unselectedIconColor = unselectedColor,
            indicatorColor = Color.Transparent,
        ),
        selected = isSelected,
        onClick = { onClick(item) },
    )
}
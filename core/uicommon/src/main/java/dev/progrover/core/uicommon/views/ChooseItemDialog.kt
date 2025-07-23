package dev.progrover.core.uicommon.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import dev.progrover.core.base.model.ListItem
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.shmr_finance.core.uicommon.R

@Composable
fun ChooseItemDialog(
    modifier: Modifier,
    items: List<ListItem>,
    onItemClick: (ListItem) -> Unit,
    onCloseClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.4f))
            .noRippleClickable({
                onCloseClick()
            }),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier
                .padding(
                    vertical = AppTheme.paddings.padding54,
                    horizontal = AppTheme.paddings.padding16
                )
                .bottomNavigationPadding()
                .fillMaxSize()
                .clip(RoundedCornerShape(AppTheme.sizes.size15))
                .background(AppTheme.colors.surface)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(AppTheme.paddings.padding12)
                        .size(AppTheme.sizes.size24)
                        .noRippleClickable ({
                            onCloseClick()
                        }),
                    imageVector = ImageVector.vectorResource(R.drawable.cross),
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                items.forEach { item ->
                    DefaultListItem(
                        modifier = Modifier,
                        title = item.name,
                        iconBackgroundColor = AppTheme.colors.secondary,
                        verticalTextPadding = AppTheme.paddings.padding16,
                        startIcon = item.caption,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}
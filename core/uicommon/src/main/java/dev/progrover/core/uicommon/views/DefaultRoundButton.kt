package dev.progrover.core.uicommon.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.shmr_finance.core.uicommon.R

@Composable
fun DefaultRoundButton(
    modifier: Modifier,
    iconId: Int = R.drawable.add,
    onClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .noRippleClickable { onClick() }
            .clip(CircleShape)
            .size(56.dp)
            .background(AppTheme.colors.brightGreen),
        contentAlignment = Alignment.Center,
    ) {

        Image(
            modifier = Modifier,
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = null,
        )
    }
}
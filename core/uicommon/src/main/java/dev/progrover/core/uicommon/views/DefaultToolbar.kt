package dev.progrover.core.uicommon.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.utils.noRippleClickable

@Composable
fun DefaultToolbar(
    modifier: Modifier,
    title: String,
    backgroundColor: Color = AppTheme.colors.brightGreen,
    leftIconId: Int? = null,
    rightIconId: Int? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(
                horizontal = AppTheme.paddings.padding4,
                vertical = AppTheme.paddings.padding8,
            )
    ) {

        if (leftIconId != null) {

            Image(
                modifier = Modifier
                    .conditionally(
                        condition =
                            onLeftIconClick != null,
                        trueExtension = {
                            noRippleClickable { onLeftIconClick?.invoke() }
                        })
                    .align(Alignment.CenterVertically)
                    .padding(AppTheme.paddings.padding12)
                    .size(AppTheme.sizes.size24),
                imageVector = ImageVector.vectorResource(leftIconId),
                contentDescription = null,
            )
        } else {
            Box(Modifier.size(AppTheme.sizes.size48))
        }

        Spacer(Modifier.weight(1f))

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = title,
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colors.textMain,
        )

        Spacer(Modifier.weight(1f))

        if (rightIconId != null) {

            Image(
                modifier = Modifier
                    .conditionally(
                        condition =
                            onRightIconClick != null,
                        trueExtension = {
                            noRippleClickable { onRightIconClick?.invoke() }
                        })
                    .align(Alignment.CenterVertically)
                    .padding(AppTheme.paddings.padding12)
                    .size(AppTheme.sizes.size24),
                imageVector = ImageVector.vectorResource(rightIconId),
                contentDescription = null,
            )
        } else {
            Box(Modifier.size(AppTheme.sizes.size48))
        }
    }
}
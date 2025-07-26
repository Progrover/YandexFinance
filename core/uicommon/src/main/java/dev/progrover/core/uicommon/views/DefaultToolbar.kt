package dev.progrover.core.uicommon.views

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import dev.progrover.core.base.utils.LocalVibrationType
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.utils.noRippleClickable

@Composable
fun DefaultToolbar(
    modifier: Modifier,
    title: String,
    backgroundColor: Color = AppTheme.colors.main,
    @DrawableRes leftIconId: Int? = null,
    @DrawableRes rightIconId: Int? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
) {
    val vibrationMode = LocalVibrationType.current

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
                            noRippleClickable(
                                { onLeftIconClick?.invoke() },
                                vibrationMode
                            )
                        }
                    )
                    .align(Alignment.CenterVertically)
                    .padding(AppTheme.paddings.padding12)
                    .size(AppTheme.sizes.size24),
                imageVector = ImageVector.vectorResource(leftIconId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.textMain)
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
                            noRippleClickable(
                                { onRightIconClick?.invoke() },
                                vibrationMode
                            )
                        }
                    )
                    .align(Alignment.CenterVertically)
                    .padding(AppTheme.paddings.padding12)
                    .size(AppTheme.sizes.size24),
                imageVector = ImageVector.vectorResource(rightIconId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.textMain)
            )
        } else {
            Box(Modifier.size(AppTheme.sizes.size48))
        }
    }
}
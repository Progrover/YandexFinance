package dev.progrover.core.uicommon.views

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.AlertType
import dev.progrover.core.base.model.ServerError
import dev.progrover.core.base.model.ServerError.TokenError
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.delay

@Composable
fun CustomAlertDialog(
    modifier: Modifier,
    alert: Alert,
    @DrawableRes endIconRes: Int? = R.drawable.cross,
    dismissTime: DismissTime = when (alert) {
        TokenError -> DismissTime.NoDismiss
        ServerError.InternetError -> DismissTime.Long
        else -> DismissTime.Short
    },
    onDismiss: () -> Unit = {},
    onClose: () -> Unit = when (dismissTime == DismissTime.NoDismiss) {
        true -> ({})
        false -> onDismiss
    },
) {
    var visible by remember { mutableStateOf(false) }
    val backgroundColor = when (alert.type) {
        AlertType.Error -> AppTheme.colors.error
        AlertType.Success -> AppTheme.colors.brightGreen
    }

    LaunchedEffect(Unit) {
        visible = true
        when (dismissTime) {
            DismissTime.Short -> {
                delay(4000)
                visible = false
                delay(2000)
                onDismiss.invoke()
            }

            DismissTime.Long -> {
                delay(7000)
                visible = false
                delay(2000)
                onDismiss.invoke()
            }

            DismissTime.NoDismiss -> Unit
        }
    }

    AnimatedVisibility(
        visible = visible,
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 74.dp)
            .padding(horizontal = AppTheme.paddings.padding8)
            .fillMaxWidth(),
        enter = fadeIn(
            tween(1000),
        ),
        exit = fadeOut(tween(1000))
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .padding(AppTheme.paddings.padding12),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding6)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(AppTheme.sizes.size24),
                imageVector = ImageVector.vectorResource(when(alert.type) {
                    AlertType.Error -> R.drawable.error
                    AlertType.Success -> R.drawable.tick
                }),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.white),
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = stringResource(alert.messageId),
                style = AppTheme.typography.bodyLarge.copy(fontWeight = W500),
                color = AppTheme.colors.white,
                textAlign = TextAlign.Center,
            )

            if (endIconRes != null && dismissTime != DismissTime.NoDismiss) {
                Image(
                    modifier = Modifier
                        .noRippleClickable { onClose() }
                        .align(Alignment.CenterVertically)
                        .size(AppTheme.sizes.size24),
                    imageVector = ImageVector.vectorResource(endIconRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.white),
                )
            } else {
                Box(Modifier.size(AppTheme.sizes.size24))
            }
        }
    }
}

enum class DismissTime {
    Short,
    Long,
    NoDismiss,
}
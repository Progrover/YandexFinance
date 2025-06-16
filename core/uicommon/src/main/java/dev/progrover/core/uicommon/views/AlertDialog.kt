package dev.progrover.core.uicommon.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable
import kotlinx.coroutines.delay

@Composable
fun CustomAlertDialog(
    modifier: Modifier,
    backgroundColor: Color = AppTheme.colors.error,
    text: String,
    additionalText: String? = null,
    dismissTime: DismissTime = DismissTime.NoDismiss,
    onDismiss: (() -> Unit)? = null,
    onClick: () -> Unit = { },
) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        when (dismissTime) {
            DismissTime.Short -> {
                delay(4000)
                visible = false
                delay(2000)
                onDismiss?.invoke()
            }

            DismissTime.Long -> {
                delay(7000)
                visible = false
                delay(2000)
                onDismiss?.invoke()
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

        Box(
            modifier = modifier
                .noRippleClickable { onClick() }
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .padding(
                    vertical = AppTheme.paddings.padding4,
                    horizontal = AppTheme.paddings.padding8
                ),
            contentAlignment = Alignment.Center,
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding6),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    modifier = Modifier,
                    text = text,
                    style = AppTheme.typography.bodyLarge.copy(fontWeight = W500),
                    color = AppTheme.colors.white,
                    textAlign = TextAlign.Center,
                )

                if (!additionalText.isNullOrBlank()) {
                    Text(
                        modifier = Modifier,
                        text = additionalText,
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.white,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

enum class DismissTime {
    Short,
    Long,
    NoDismiss,
}
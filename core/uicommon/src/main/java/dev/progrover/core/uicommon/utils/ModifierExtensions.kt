package dev.progrover.core.uicommon.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import dev.progrover.core.theme.AppTheme

@Composable
fun Modifier.bottomNavigationPadding() =
    this
        .padding(bottom = AppTheme.sizes.size80)
        .navigationBarsPadding()

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            onClick()
        }
    }

@Composable
fun Modifier.conditionally(
    condition: Boolean,
    trueExtension: Modifier.() -> Modifier,
    falseExtension: (Modifier.() -> Modifier)? = null,
): Modifier =
    if (condition) this.trueExtension() else falseExtension?.invoke(this) ?: this

package dev.progrover.expenditures.impl.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import dev.progrover.core.theme.AppTheme
import dev.progrover.shmr_finance.feature.expenditures.impl.R
import kotlinx.coroutines.delay

@Composable
internal fun AppLogo(
    modifier: Modifier,
    afterAnimation: () -> Unit,
) {

    val alphaImage = remember { Animatable(0f) }
    val alphaText = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(200)
        alphaImage.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000),
        )
        alphaText.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000),
        )
        afterAnimation()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = modifier,
        ) {

            Image(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = alphaImage.value
                    }
                    .align(Alignment.CenterHorizontally)
                    .size(AppTheme.sizes.size180)
                    .padding(bottom = AppTheme.paddings.padding10),
                imageVector = ImageVector.vectorResource(dev.progrover.shmr_finance.core.uicommon.R.drawable.logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.brightGreen)
            )

            Text(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = alphaText.value
                    }
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.greeting_title),
                style = AppTheme.typography.titleLarge.copy(fontWeight = W700)
            )
        }
    }
}

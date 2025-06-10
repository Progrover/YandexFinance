package dev.progrover.expenditures.impl.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import dev.progrover.core.theme.AppTheme
import dev.progrover.shmr_finance.feature.expenditures.impl.R

@Composable
internal fun AppLogo(
    modifier: Modifier,
    isLogoVisible: Boolean,
    isTextVisible: Boolean,
) {

    Column(
        modifier = modifier,
    ) {

        AnimatedVisibility(
            visible = isLogoVisible,
            enter = fadeIn(animationSpec = tween(3000)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(AppTheme.sizes.size180)
        ) {

            Image(
                modifier = Modifier
                    .padding(bottom = AppTheme.paddings.padding10),
                imageVector = ImageVector.vectorResource(dev.progrover.shmr_finance.core.uicommon.R.drawable.logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.brightGreen)
            )
        }

        if (!isTextVisible) {
            Spacer(Modifier.height(AppTheme.sizes.size20))
        }

        AnimatedVisibility(
            visible = isTextVisible,
            enter = fadeIn(animationSpec = tween(3000)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(AppTheme.sizes.size20)
        ) {

            Text(
                modifier = Modifier,
                text = stringResource(R.string.greeting_title),
                style = AppTheme.typography.titleLarge.copy(fontWeight = W700)
            )
        }
    }
}
package dev.progrover.core.uicommon.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import dev.progrover.core.theme.AppTheme

@Composable
fun ProgressIndicator() {

    Box(Modifier.fillMaxSize()) {

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center),
            color = AppTheme.colors.brightGreen,
            strokeCap = StrokeCap.Round,
            trackColor = AppTheme.colors.surface,
        )
    }
}
package dev.progrover.settings.impl.presentation.components.screencontent

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.progrover.core.base.utils.toDatePresentation
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.contract.about.AboutUIEvent
import dev.progrover.settings.impl.presentation.contract.about.AboutUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun AboutScreenContent(
    modifier: Modifier,
    uiState: AboutUIState,
    onEvent: (AboutUIEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
    ) {
        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            toolbar = {

                DefaultToolbar(
                    modifier = Modifier,
                    backgroundColor = AppTheme.colors.surface,
                    title = stringResource(R.string.about),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(AboutUIEvent.OnBackClick) }
                )
            },
        ) {

            DefaultListItem(
                modifier = Modifier,
                verticalTextPadding = AppTheme.paddings.padding12,
                title = stringResource(R.string.app_version),
                dividerVisible = false,
                additionalText = uiState.versionName,
                titleColor = AppTheme.colors.textMain,
                onClick = { }
            )

            DefaultListItem(
                modifier = Modifier,
                verticalTextPadding = AppTheme.paddings.padding12,
                title = stringResource(R.string.last_update),
                dividerVisible = false,
                additionalText = getLastUpdateTime(LocalContext.current).toDatePresentation(),
                titleColor = AppTheme.colors.textMain,
                onClick = { }
            )
        }
    }
}

fun getLastUpdateTime(context: Context): Long {
    val pm = context.packageManager
    val packageInfo = pm.getPackageInfo(context.packageName, 0)
    return packageInfo.lastUpdateTime
}
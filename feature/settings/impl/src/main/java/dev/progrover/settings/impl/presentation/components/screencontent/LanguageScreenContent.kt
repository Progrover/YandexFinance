package dev.progrover.settings.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.progrover.core.base.utils.LocaleVariant
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.settings.impl.presentation.contract.language.LanguageUIEvent
import dev.progrover.settings.impl.presentation.contract.language.LanguageUIState
import dev.progrover.shmr_finance.feature.settings.impl.R

@Composable
internal fun LanguageScreenContent(
    modifier: Modifier,
    uiState: LanguageUIState,
    onEvent: (LanguageUIEvent) -> Unit,
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
                    title = stringResource(R.string.language),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(LanguageUIEvent.OnBackClick) }
                )
            },
        ) {

            DefaultListItem(
                modifier = Modifier,
                title = stringResource(R.string.russian),
                titleColor = AppTheme.colors.textMain,
                onClick = { onEvent(LanguageUIEvent.OnLanguageClick(LocaleVariant.Russian)) }
            )

            DefaultListItem(
                modifier = Modifier,
                title = stringResource(R.string.english),
                titleColor = AppTheme.colors.textMain,
                onClick = { onEvent(LanguageUIEvent.OnLanguageClick(LocaleVariant.English)) }
            )
        }
    }
}
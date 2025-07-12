package dev.progrover.account.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.progrover.account.impl.presentation.contract.currency.CurrencyUIEvent
import dev.progrover.account.impl.presentation.contract.currency.CurrencyUIState
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.getCurrency
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.BottomSheetHandle
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.shmr_finance.feature.account.impl.R

@Composable
internal fun CurrencyScreenContent(
    modifier: Modifier,
    uiState: CurrencyUIState,
    onEvent: (CurrencyUIEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .clip(
                RoundedCornerShape(
                    topStart = AppTheme.paddings.padding16,
                    topEnd = AppTheme.paddings.padding16,
                )
            )
            .background(AppTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        BottomSheetHandle(
            modifier = Modifier
                .padding(AppTheme.paddings.padding16)
                .size(width = 32.dp, height = 4.dp)
        )
        uiState.currencyList.forEach { currency ->
            DefaultListItem(
                modifier = Modifier,
                backgroundColor = AppTheme.colors.white,
                iconBackgroundColor = AppTheme.colors.white,
                title = stringResource(currency.second).addCurrency(currency.first),
                startIcon = currency.first.getCurrency(false),
                verticalTextPadding = AppTheme.paddings.padding16,
                onClick = { onEvent(CurrencyUIEvent.OnItemClick(currency.first)) },
            )
        }

        DefaultListItem(
            modifier = Modifier,
            backgroundColor = AppTheme.colors.error,
            title = stringResource(R.string.cancel),
            titleColor = AppTheme.colors.white,
            dividerVisible = false,
            iconBackgroundColor = AppTheme.colors.error,
            startIconResId = R.drawable.cancel,
            verticalTextPadding = AppTheme.paddings.padding16,
            onClick = { onEvent(CurrencyUIEvent.OnCancelClick) },
        )
    }
}
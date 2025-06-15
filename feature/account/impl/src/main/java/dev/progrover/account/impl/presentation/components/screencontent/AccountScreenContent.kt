package dev.progrover.account.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.progrover.account.impl.presentation.contract.account.AccountUIEvent
import dev.progrover.account.impl.presentation.contract.account.AccountUIState
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultRoundButton
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.shmr_finance.feature.account.impl.R

@Composable
internal fun AccountScreenContent(
    modifier: Modifier,
    uiState: AccountUIState,
    onEvent: (AccountUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {

        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            toolbar = {

                DefaultToolbar(
                    modifier = Modifier,
                    title = stringResource(R.string.account_title),
                    rightIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.edit,
                    onRightIconClick = { onEvent(AccountUIEvent.OnEditClick) }
                )
            },
        ) {

            DefaultListItem(
                modifier = Modifier,
                backgroundColor = AppTheme.colors.paleGreen,
                iconBackgroundColor = AppTheme.colors.white,
                startIcon = "\uD83D\uDCB0",
                title = stringResource(R.string.total_amount),
                verticalTextPadding = AppTheme.paddings.padding8,
                additionalText = uiState.totalAmount,
                endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                onClick = { onEvent(AccountUIEvent.OnTotalAmountClick) },
            )

            DefaultListItem(
                modifier = Modifier,
                backgroundColor = AppTheme.colors.paleGreen,
                title = stringResource(R.string.currency),
                additionalText = uiState.currency,
                dividerVisible = false,
                verticalTextPadding = AppTheme.paddings.padding8,
                endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                onClick = { onEvent(AccountUIEvent.OnCurrencyClick) },
            )
        }

        DefaultRoundButton(
            modifier = Modifier
                .padding(
                    end = AppTheme.paddings.padding16,
                    bottom = AppTheme.paddings.padding14,
                )
                .align(Alignment.BottomEnd),
            onClick = { onEvent(AccountUIEvent.OnAddClick) }
        )

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )
    }
}
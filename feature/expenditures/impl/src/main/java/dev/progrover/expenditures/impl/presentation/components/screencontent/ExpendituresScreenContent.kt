package dev.progrover.expenditures.impl.presentation.components.screencontent

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
import dev.progrover.core.base.utils.Variables
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultRoundButton
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.core.uicommon.views.DismissTime
import dev.progrover.core.uicommon.views.ProgressIndicator
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEvent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIState
import dev.progrover.shmr_finance.feature.expenditures.impl.R

@Composable
internal fun ExpendituresScreenContent(
    modifier: Modifier,
    uiState: ExpendituresUIState,
    onEvent: (ExpendituresUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {
        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .conditionally(
                    condition = !uiState.isLoading,
                    trueExtension = {
                        verticalScroll(scrollState)
                    }),
            toolbar = {

                DefaultToolbar(
                    modifier = Modifier,
                    title = stringResource(R.string.expenditures_title),
                    rightIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.refresh,
                    onRightIconClick = { onEvent(ExpendituresUIEvent.OnRefreshClick) }
                )
            },
        ) {

            DefaultListItem(
                modifier = Modifier,
                backgroundColor = AppTheme.colors.paleGreen,
                title = stringResource(R.string.total),
                verticalTextPadding = AppTheme.paddings.padding8,
                additionalText = uiState.totalExpenditures,
                onClick = { onEvent(ExpendituresUIEvent.OnAllExpendituresClick) },
            )

            if (!uiState.isLoading) {
                uiState.expenditures.forEach { expenditure ->
                    DefaultListItem(
                        modifier = Modifier,
                        title = expenditure.name,
                        startIcon = expenditure.emoji,
                        captionTitle = expenditure.comment,
                        additionalText = expenditure.amount,
                        verticalTextPadding = when (expenditure.comment.isNullOrBlank()) {
                            true -> AppTheme.paddings.padding14
                            false -> AppTheme.paddings.padding4
                        },
                        endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                        onClick = { onEvent(ExpendituresUIEvent.OnExpenditureItemClick(expenditure.id)) }
                    )
                }
            } else ProgressIndicator()
        }

        DefaultRoundButton(
            modifier = Modifier
                .padding(
                    end = AppTheme.paddings.padding16,
                    bottom = AppTheme.paddings.padding14,
                )
                .align(Alignment.BottomEnd),
            iconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.add,
            onClick = { onEvent(ExpendituresUIEvent.OnAddExpenditureClick) }
        )

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )

        if (!uiState.error.isNullOrEmpty())
            CustomAlertDialog(
                modifier = Modifier,
                text = when (uiState.error) {
                    Variables.INTERNET_ERROR -> stringResource(dev.progrover.shmr_finance.core.uicommon.R.string.internet_error)
                    Variables.TOKEN_ERROR -> stringResource(dev.progrover.shmr_finance.core.uicommon.R.string.token_error)
                    else -> stringResource(dev.progrover.shmr_finance.core.uicommon.R.string.unknown_error)
                },
                additionalText = when (uiState.error) {
                    Variables.INTERNET_ERROR -> stringResource(dev.progrover.shmr_finance.core.uicommon.R.string.internet_error_subtitle)
                    Variables.TOKEN_ERROR -> stringResource(dev.progrover.shmr_finance.core.uicommon.R.string.token_error_subtitle)
                    else -> stringResource(dev.progrover.shmr_finance.core.uicommon.R.string.unknown_error_subtitle)
                },
                dismissTime = when (uiState.error) {
                    Variables.TOKEN_ERROR -> DismissTime.NoDismiss
                    else -> DismissTime.Short
                },
                onDismiss = { onEvent(ExpendituresUIEvent.OnErrorDialogDone) }
            )
    }
}
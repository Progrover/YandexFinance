package dev.progrover.history.impl.presentation.components.screencontent

import DateDialog
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
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.core.base.utils.toDatePresentation
import dev.progrover.core.base.utils.toDateTimePresentation
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.core.uicommon.views.ProgressIndicator
import dev.progrover.history.impl.presentation.contract.history.DatePickerState
import dev.progrover.history.impl.presentation.contract.history.HistoryUIEvent
import dev.progrover.history.impl.presentation.contract.history.HistoryUIState
import dev.progrover.shmr_finance.feature.history.impl.R

@Composable
internal fun HistoryScreenContent(
    modifier: Modifier,
    uiState: HistoryUIState,
    onEvent: (HistoryUIEvent) -> Unit,
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
                    title = stringResource(R.string.history),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    rightIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.history,
                    onRightIconClick = { onEvent(HistoryUIEvent.OnAnalyseClick) },
                    onLeftIconClick = { onEvent(HistoryUIEvent.OnBackClick) }
                )

                DefaultListItem(
                    modifier = Modifier,
                    backgroundColor = AppTheme.colors.paleGreen,
                    title = stringResource(R.string.start),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    additionalText = uiState.start.toDatePresentation(),
                    onClick = { onEvent(HistoryUIEvent.OnStartClick) },
                )

                DefaultListItem(
                    modifier = Modifier,
                    backgroundColor = AppTheme.colors.paleGreen,
                    title = stringResource(R.string.end),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    additionalText = uiState.end.toDatePresentation(),
                    onClick = { onEvent(HistoryUIEvent.OnEndClick) },
                )

                DefaultListItem(
                    modifier = Modifier,
                    backgroundColor = AppTheme.colors.paleGreen,
                    title = stringResource(R.string.sum),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    additionalText = uiState.total,
                    dividerVisible = false,
                    onClick = { },
                )
            },
        ) {

            if (!uiState.isLoading) {
                uiState.history.forEach { history ->
                    DefaultListItem(
                        modifier = Modifier,
                        title = history.name,
                        startIcon = history.emoji,
                        captionTitle = history.comment,
                        additionalText = history.amount.formatToAmount()
                            .addCurrency(uiState.currency),
                        captionAdditional = history.dateTime.toDateTimePresentation(),
                        verticalTextPadding = when (history.comment.isNullOrBlank()) {
                            true -> AppTheme.paddings.padding14
                            false -> AppTheme.paddings.padding4
                        },
                        endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                        onClick = { }
                    )
                }
            } else ProgressIndicator()
        }
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )

        if (uiState.showDatePicker == DatePickerState.StartPick)
            DateDialog(
                selectedDate = uiState.startForPresentation,
                onOkClick = { millis ->
                    onEvent(HistoryUIEvent.OnNewDateSelected(millis, uiState.showDatePicker))
                },
                onCancelClick = {
                    onEvent(
                        HistoryUIEvent.OnDatePickerClose
                    )
                },
            )

        if (uiState.showDatePicker == DatePickerState.EndPick)
            DateDialog(
                selectedDate = uiState.end,
                onOkClick = { millis ->
                    onEvent(HistoryUIEvent.OnNewDateSelected(millis, uiState.showDatePicker))
                },
                onCancelClick = {
                    onEvent(
                        HistoryUIEvent.OnDatePickerClose
                    )
                },
            )

        if (uiState.error != null)
            CustomAlertDialog(
                modifier = Modifier,
                error = uiState.error,
                onDismiss = { onEvent(HistoryUIEvent.OnErrorDialogDone) }
            )
    }
}
package dev.progrover.history.impl.presentation.components.screencontent

import DateDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.progrover.core.base.utils.addCurrency
import dev.progrover.core.base.utils.formatToAmount
import dev.progrover.core.base.utils.monthAndYear
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.CustomListItem
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.core.uicommon.views.ProgressIndicator
import dev.progrover.history.impl.domain.model.DatePickerState
import dev.progrover.history.impl.presentation.contract.analysis.AnalysisUIEvent
import dev.progrover.history.impl.presentation.contract.analysis.AnalysisUIState
import dev.progrover.shmr_finance.feature.history.impl.R

@Composable
internal fun AnalysisScreenContent(
    modifier: Modifier,
    uiState: AnalysisUIState,
    onEvent: (AnalysisUIEvent) -> Unit,
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
                    }
                ),
            toolbar = {
                DefaultToolbar(
                    modifier = Modifier,
                    title = stringResource(R.string.analysis),
                    backgroundColor = AppTheme.colors.surface,
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow,
                    onLeftIconClick = { onEvent(AnalysisUIEvent.OnBackClick) }
                )
            },
        ) {

            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.analysis_start),
                verticalPadding = AppTheme.paddings.padding8,
                customElement = {
                    Row {

                        Spacer(Modifier.weight(1f))
                        DefaultFloatingButton(
                            modifier = Modifier,
                            text = uiState.start.monthAndYear(),
                            cornerRadius = 100.dp,
                            innerVerticalPadding = AppTheme.paddings.padding6,
                            innerHorizontalPadding = AppTheme.paddings.padding10,
                            backgroundColor = AppTheme.colors.main,
                            onClick = { onEvent(AnalysisUIEvent.OnStartClick) },
                        )

                    }
                },
                onClick = { },
            )

            CustomListItem(
                modifier = Modifier,
                title = stringResource(R.string.analysis_end),
                verticalPadding = AppTheme.paddings.padding8,
                customElement = {
                    Row {

                        Spacer(Modifier.weight(1f))

                        DefaultFloatingButton(
                            modifier = Modifier,
                            text = uiState.end.monthAndYear(),
                            cornerRadius = 100.dp,
                            innerVerticalPadding = AppTheme.paddings.padding6,
                            innerHorizontalPadding = AppTheme.paddings.padding10,
                            backgroundColor = AppTheme.colors.main,
                            onClick = { onEvent(AnalysisUIEvent.OnEndClick) },
                        )
                    }
                },
                onClick = { },
            )

            DefaultListItem(
                modifier = Modifier,
                title = stringResource(R.string.sum),
                verticalTextPadding = AppTheme.paddings.padding8,
                additionalText = uiState.total,
                onClick = { },
            )

            Spacer(Modifier.height(180.dp))

            if (!uiState.isLoading) {
                if (uiState.analysis.isNotEmpty()) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = AppTheme.colors.border,
                    )

                    uiState.analysis.forEach { analysis ->
                        DefaultListItem(
                            modifier = Modifier,
                            title = analysis.name,
                            startIcon = analysis.emoji,
                            captionTitle = analysis.comment,
                            additionalText = "${analysis.percentage} %",
                            captionAdditional = analysis.amount.formatToAmount()
                                .addCurrency(uiState.currency),
                            verticalTextPadding = when (analysis.comment.isNullOrBlank()) {
                                true -> AppTheme.paddings.padding14
                                false -> AppTheme.paddings.padding4
                            },
                            endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                            onClick = { onEvent(AnalysisUIEvent.OnAnalysisItemClick(analysis.id)) }
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = AppTheme.paddings.padding8),
                        text = stringResource(R.string.nothing_to_analyse),
                        textAlign = TextAlign.Center,
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.labelMedium
                    )
                }
            } else {
                ProgressIndicator()
            }
        }
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )

        if (uiState.showDatePicker == DatePickerState.StartPick) {
            DateDialog(
                selectedDate = uiState.startForPresentation,
                onOkClick = { millis ->
                    onEvent(AnalysisUIEvent.OnNewDateSelected(millis, uiState.showDatePicker))
                },
                onCancelClick = {
                    onEvent(
                        AnalysisUIEvent.OnDatePickerClose
                    )
                },
            )
        }

        if (uiState.showDatePicker == DatePickerState.EndPick) {
            DateDialog(
                selectedDate = uiState.endForPresentation,
                onOkClick = { millis ->
                    onEvent(AnalysisUIEvent.OnNewDateSelected(millis, uiState.showDatePicker))
                },
                onCancelClick = {
                    onEvent(
                        AnalysisUIEvent.OnDatePickerClose
                    )
                },
            )
        }

        if (uiState.alert != null) {
            CustomAlertDialog(
                modifier = Modifier,
                alert = uiState.alert,
                onDismiss = { onEvent(AnalysisUIEvent.OnErrorDialogDone) }
            )
        }
    }
}
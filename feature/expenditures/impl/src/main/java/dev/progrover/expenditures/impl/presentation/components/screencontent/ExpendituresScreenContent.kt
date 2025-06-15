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
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultRoundButton
import dev.progrover.core.uicommon.views.DefaultToolbar
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
    }
}
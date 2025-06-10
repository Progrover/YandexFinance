package dev.progrover.incomes.impl.presentation.components.screencontent

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
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEvent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIState
import dev.progrover.shmr_finance.feature.incomes.impl.R

@Composable
internal fun IncomesScreenContent(
    modifier: Modifier,
    uiState: IncomesUIState,
    onEvent: (IncomesUIEvent) -> Unit,
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
                    title = stringResource(R.string.incomes_title),
                    rightIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.refresh,
                    onRightIconClick = { onEvent(IncomesUIEvent.OnRefreshClick) }
                )
            },
        ) {

            DefaultListItem(
                modifier = Modifier,
                backgroundColor = AppTheme.colors.paleGreen,
                title = stringResource(R.string.total),
                additionalText = uiState.totalIncomes,
                onClick = { onEvent(IncomesUIEvent.OnAllIncomesClick) },
            )

            uiState.incomes.forEach { income ->
                DefaultListItem(
                    modifier = Modifier,
                    title = income.name,
                    startIcon = income.emoji,
                    captionTitle = income.comment,
                    additionalText = income.amount,
                    endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                    onClick = { onEvent(IncomesUIEvent.OnIncomeItemClick(income.id)) }
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
            onClick = { onEvent(IncomesUIEvent.OnAddIncomeClick) }
        )

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )
    }
}
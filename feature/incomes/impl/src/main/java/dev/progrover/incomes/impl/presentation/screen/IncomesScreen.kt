package dev.progrover.incomes.impl.presentation.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.feature.edit.api.EditFeature
import dev.progrover.history.api.HistoryFeature
import dev.progrover.incomes.impl.presentation.components.screencontent.IncomesScreenContent
import dev.progrover.incomes.impl.presentation.contract.incomes.IncomesUIEffect
import dev.progrover.incomes.impl.presentation.viewmodel.IncomesViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun IncomesScreen(
    navController: NavController,
    viewModel: IncomesViewModel,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is IncomesUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.messageResId),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )

                IncomesUIEffect.NavigateToHistoryScreen ->
                    HistoryFeature.openHistoryScreen(navController, RouteDesc.Incomes)

                IncomesUIEffect.NavigateToAddTransactionScreen ->
                    EditFeature.openAddScreen(
                        navController = navController,
                        transactionType = RouteDesc.Incomes,
                    )

                is IncomesUIEffect.NavigateToEditTransactionScreen ->
                    EditFeature.openEditScreen(
                        navController = navController,
                        transactionType = RouteDesc.Incomes,
                        transactionId = effect.id
                    )
            }
        }
    }

    IncomesScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}
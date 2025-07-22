package dev.progrover.history.impl.presentation.screen

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
import dev.progrover.feature.edit.api.EditFeature
import dev.progrover.history.impl.presentation.components.screencontent.AnalysisScreenContent
import dev.progrover.history.impl.presentation.contract.analysis.AnalysisUIEffect
import dev.progrover.history.impl.presentation.viewmodel.AnalysisViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AnalysisScreen(
    navController: NavController,
    viewModel: AnalysisViewModel,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AnalysisUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.messageResId),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )

                AnalysisUIEffect.NavigateBack ->
                    navController.popBackStack()

                is AnalysisUIEffect.NavigateToEditTransactionScreen ->
                    EditFeature.openEditScreen(
                        navController = navController,
                        transactionType = effect.transactionType,
                        transactionId = effect.id
                    )
            }
        }
    }

    AnalysisScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}

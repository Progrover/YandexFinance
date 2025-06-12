package dev.progrover.expenditures.impl.presentation.screen

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
import dev.progrover.expenditures.impl.presentation.components.screencontent.ExpendituresScreenContent
import dev.progrover.expenditures.impl.presentation.contract.expenditures.ExpendituresUIEffect
import dev.progrover.expenditures.impl.presentation.viewmodel.ExpendituresViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ExpendituresScreen(
    navController: NavController,
    viewModel: ExpendituresViewModel,
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {

                is ExpendituresUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.messageResId),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
            }
        }
    }

    ExpendituresScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}
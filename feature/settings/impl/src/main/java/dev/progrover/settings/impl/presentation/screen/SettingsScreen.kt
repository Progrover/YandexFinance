package dev.progrover.settings.impl.presentation.screen

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
import dev.progrover.settings.impl.presentation.components.screencontent.SettingsScreenContent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEffect
import dev.progrover.settings.impl.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel,
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {

                is SettingsUIEffect.ShowError ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.messageResId),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
            }
        }
    }

    SettingsScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
        snackbarHostState = snackbarHostState,
    )
}
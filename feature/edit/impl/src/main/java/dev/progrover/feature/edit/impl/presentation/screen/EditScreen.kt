package dev.progrover.edit.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.feature.edit.impl.presentation.components.screencontent.EditScreenContent
import dev.progrover.feature.edit.impl.presentation.contract.edit.EditUIEffect
import dev.progrover.feature.edit.impl.presentation.viewmodel.EditViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun EditScreen(
    navController: NavController,
    viewModel: EditViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                EditUIEffect.NavigateBack ->
                    navController.popBackStack()
            }
        }
    }

    EditScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}
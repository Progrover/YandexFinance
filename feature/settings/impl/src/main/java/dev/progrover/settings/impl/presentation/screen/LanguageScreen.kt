package dev.progrover.settings.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.settings.impl.presentation.components.screencontent.LanguageScreenContent
import dev.progrover.settings.impl.presentation.contract.language.LanguageUIEffect
import dev.progrover.settings.impl.presentation.viewmodel.LanguageViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun LanguageScreen(
    navController: NavController,
    viewModel: LanguageViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                LanguageUIEffect.NavigateBack ->
                    navController.popBackStack()
            }
        }
    }

    LanguageScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}
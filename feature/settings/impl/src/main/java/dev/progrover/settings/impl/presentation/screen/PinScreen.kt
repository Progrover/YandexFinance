package dev.progrover.settings.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.feature.auth.api.AuthFeature
import dev.progrover.settings.impl.presentation.components.screencontent.PinScreenContent
import dev.progrover.settings.impl.presentation.contract.pin.PinUIEffect
import dev.progrover.settings.impl.presentation.viewmodel.PinViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun PinScreen(
    navController: NavController,
    viewModel: PinViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                PinUIEffect.NavigateBack ->
                    navController.popBackStack()

                is PinUIEffect.NavigateToPinScreen ->
                    AuthFeature.openAuthScreen(navController, effect.type)
            }
        }
    }

    PinScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}
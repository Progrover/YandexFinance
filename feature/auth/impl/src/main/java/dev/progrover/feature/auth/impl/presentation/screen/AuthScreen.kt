package dev.progrover.feature.auth.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.expenditures.api.ExpendituresFeature
import dev.progrover.feature.auth.impl.presentation.components.screencontent.AuthScreenContent
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIEffect
import dev.progrover.feature.auth.impl.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                AuthUIEffect.NavigateBack ->
                    navController.popBackStack()

                AuthUIEffect.NavigateToExpendsScreen ->
                    ExpendituresFeature.openExpendituresScreen(
                        navController
                    )
            }
        }
    }

    AuthScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}

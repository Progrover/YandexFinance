package dev.progrover.expenditures.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.expenditures.impl.presentation.components.screencontent.GreetingScreenContent
import dev.progrover.expenditures.impl.presentation.viewmodel.GreetingViewModel
import dev.progrover.expenditures.api.ExpendituresFeature
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun GreetingScreen(
    navController: NavController,
    viewModel: GreetingViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                GreetingUIEffect.NavigateToExpendituresScreen ->
                    ExpendituresFeature.openExpendituresScreen(navController, true)
            }
        }
    }

    GreetingScreenContent(
        modifier = Modifier,
        uiState = uiState,
        onEvent = viewModel::setEvent,
    )
}
package dev.progrover.account.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.account.impl.presentation.components.screencontent.CurrencyScreenContent
import dev.progrover.account.impl.presentation.contract.currency.CurrencyUIEffect
import dev.progrover.account.impl.presentation.viewmodel.CurrencyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun CurrencyScreen(
    navController: NavController,
    viewModel: CurrencyViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {

                CurrencyUIEffect.NavigateBack ->
                    navController.popBackStack()
            }
        }
    }

    CurrencyScreenContent(
        modifier = Modifier,
        onEvent = viewModel::setEvent,
        uiState = uiState,
    )
}
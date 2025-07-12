package dev.progrover.account.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.account.impl.presentation.components.screencontent.BalanceNameScreenContent
import dev.progrover.account.impl.presentation.contract.balance.BalanceUIEffect
import dev.progrover.account.impl.presentation.viewmodel.BalanceNameViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun BalanceNameScreen(
    navController: NavController,
    viewModel: BalanceNameViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {

                BalanceUIEffect.NavigateBack ->
                    navController.popBackStack()
            }
        }
    }

    BalanceNameScreenContent(
        modifier = Modifier,
        onEvent = viewModel::setEvent,
        uiState = uiState,
    )
}
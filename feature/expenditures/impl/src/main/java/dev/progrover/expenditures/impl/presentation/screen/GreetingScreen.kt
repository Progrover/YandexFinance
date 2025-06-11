package dev.progrover.expenditures.impl.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.progrover.expenditures.api.ExpendituresFeature
import dev.progrover.expenditures.impl.presentation.components.screencontent.GreetingScreenContent
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEffect
import dev.progrover.expenditures.impl.presentation.viewmodel.GreetingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun GreetingScreen(
    navController: NavController,
    viewModel: GreetingViewModel,
) {

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
        onEvent = viewModel::setEvent,
    )
}
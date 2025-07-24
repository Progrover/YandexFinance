package dev.progrover.feature.auth.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.feature.auth.api.domain.model.NavigationVariant
import dev.progrover.feature.auth.impl.presentation.components.PinCodeWindows
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIEvent
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIState
import dev.progrover.shmr_finance.feature.auth.impl.R

@Composable
internal fun AuthScreenContent(
    modifier: Modifier,
    uiState: AuthUIState,
    onEvent: (AuthUIEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .noRippleClickable({focusManager.clearFocus()})
    ) {
        BasicColumn(
            modifier = modifier
                .fillMaxSize(),
            toolbar = {
                DefaultToolbar(
                    modifier = Modifier,
                    title = when (uiState.task) {
                        NavigationVariant.Start -> ""
                        else -> {
                            if (uiState.secondStep)
                                stringResource(R.string.two_from_two) else
                                stringResource(R.string.one_from_two)
                        }
                    },
                    backgroundColor = AppTheme.colors.surface,
                    leftIconId = when (uiState.task) {
                        NavigationVariant.Start -> null
                        else -> {
                            dev.progrover.shmr_finance.core.uicommon.R.drawable.back_arrow
                        }
                    },
                    onLeftIconClick = { onEvent(AuthUIEvent.OnBackClick) }
                )
            },
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.paddings.padding16,
                        top = AppTheme.paddings.padding68,
                        end = AppTheme.paddings.padding16,
                        bottom = AppTheme.paddings.padding40
                    ),
                color = AppTheme.colors.textMain,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.titleLarge,
                text = stringResource(
                    when (uiState.task) {
                        NavigationVariant.Start -> R.string.welcome
                        else -> {
                            if (!uiState.secondStep)
                                R.string.set_pin else
                                R.string.set_second_pin
                        }
                    }
                )
            )

            if (uiState.secondStep) {
                PinCodeWindows(
                    modifier = Modifier,
                    isPrivate = false,
                    pinCode = uiState.secondPinCode?.toString() ?: "",
                    focusRequester = focusRequester,
                    isCorrect = uiState.pinCodeIsCorrect,
                    onPinCodeChange = { newPin ->
                        onEvent(AuthUIEvent.OnSecondPinCodeChange(newPin.filter { it.isDigit() }
                            .toInt()))
                    }
                )
            } else {
                PinCodeWindows(
                    modifier = Modifier,
                    isPrivate = uiState.task == NavigationVariant.Start,
                    pinCode = uiState.pinCode?.toString() ?: "",
                    focusRequester = focusRequester,
                    isCorrect = uiState.pinCodeIsCorrect,
                    onPinCodeChange = { newPin ->
                        onEvent(AuthUIEvent.OnFirstPinCodeChange(newPin.filter { it.isDigit() }
                            .toInt()))
                    }
                )
            }
        }

        if (uiState.alert != null) {
            CustomAlertDialog(
                modifier = Modifier,
                alert = uiState.alert,
                onDismiss = { onEvent(AuthUIEvent.OnErrorDialogDone) }
            )
        }
    }
}
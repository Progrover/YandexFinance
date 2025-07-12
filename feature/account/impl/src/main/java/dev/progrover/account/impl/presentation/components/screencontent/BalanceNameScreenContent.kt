package dev.progrover.account.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import dev.progrover.account.impl.presentation.contract.balance.BalanceUIEvent
import dev.progrover.account.impl.presentation.contract.balance.BalanceUIState
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.CustomListItem
import dev.progrover.core.uicommon.views.DefaultTextField
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.core.uicommon.views.ProgressIndicator
import dev.progrover.shmr_finance.feature.account.impl.R

@Composable
internal fun BalanceNameScreenContent(
    modifier: Modifier,
    uiState: BalanceUIState,
    onEvent: (BalanceUIEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {
        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .noRippleClickable {
                    focusManager.clearFocus()
                },
            toolbar = {
                DefaultToolbar(
                    modifier = Modifier,
                    title = stringResource(R.string.account_changes),
                    rightIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.tick,
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.cross,
                    onRightIconClick = { onEvent(BalanceUIEvent.OnConfirmClick) },
                    onLeftIconClick = { onEvent(BalanceUIEvent.OnBackClick) }
                )

                CustomListItem(
                    modifier = Modifier,
                    title = stringResource(R.string.account_name),
                    titleColor = AppTheme.colors.textMain,
                    backgroundColor = AppTheme.colors.white,
                    startIcon = "N",
                    iconBackgroundColor = AppTheme.colors.white,
                    customElement = {

                        DefaultTextField(
                            modifier = Modifier
                                .background(AppTheme.colors.white)
                                .padding(AppTheme.paddings.padding4),
                            text = uiState.account?.name ?: "",
                            dividerVisible = false,
                            enabled = uiState.account != null,
                            focusRequester = focusRequester,
                            hintResId = R.string.account_hint,
                            onTextChange = { newText -> onEvent(BalanceUIEvent.OnNameChange(newText)) }
                        )
                    }
                )

                CustomListItem(
                    modifier = Modifier,
                    title = stringResource(R.string.account_balance),
                    titleColor = AppTheme.colors.textMain,
                    backgroundColor = AppTheme.colors.white,
                    startIcon = "\uD83D\uDCB0",
                    dividerVisible = false,
                    iconBackgroundColor = AppTheme.colors.white,
                    customElement = {

                        DefaultTextField(
                            modifier = Modifier
                                .background(AppTheme.colors.white)
                                .padding(AppTheme.paddings.padding4),
                            text = uiState.account?.balance ?: "",
                            dividerVisible = false,
                            enabled = uiState.account != null,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            focusRequester = focusRequester,
                            hintResId = R.string.account_hint,
                            onTextChange = { newBalance ->
                                onEvent(
                                    BalanceUIEvent.OnBalanceChange(
                                        newBalance
                                    )
                                )
                            }
                        )
                    }
                )
            },
        ) {
            if (uiState.isLoading) ProgressIndicator()
        }

        if (uiState.alert != null) {
            CustomAlertDialog(
                modifier = Modifier,
                alert = uiState.alert,
                onDismiss = { onEvent(BalanceUIEvent.OnErrorDialogDone) }
            )
        }
    }
}
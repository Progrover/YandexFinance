package dev.progrover.feature.edit.impl.presentation.components.screencontent

import DateDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.utils.toDatePresentation
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.ChooseItemDialog
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultTextField
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.core.uicommon.views.ProgressIndicator
import dev.progrover.feature.edit.api.model.EditVatiant
import dev.progrover.feature.edit.impl.presentation.contract.edit.EditUIEvent
import dev.progrover.feature.edit.impl.presentation.contract.edit.EditUIState
import dev.progrover.shmr_finance.feature.edit.impl.R

@Composable
internal fun EditScreenContent(
    modifier: Modifier,
    uiState: EditUIState,
    onEvent: (EditUIEvent) -> Unit,
) {
    val commentFocusRequester = remember { FocusRequester() }
    val timeFocusRequester = remember { FocusRequester() }
    val amountFocusRequester = remember { FocusRequester() }


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
                    title = stringResource(
                        when (
                            uiState.actionType
                        ) {
                            EditVatiant.Add -> {
                                when (uiState.transactionType) {
                                    RouteDesc.Incomes -> R.string.add_income
                                    RouteDesc.Expenditures -> R.string.add_expenditure
                                }
                            }

                            EditVatiant.Edit -> {
                                when (uiState.transactionType) {
                                    RouteDesc.Incomes -> R.string.edit_income
                                    RouteDesc.Expenditures -> R.string.edit_expenditure
                                }
                            }
                        }
                    ),
                    leftIconId = dev.progrover.shmr_finance.core.uicommon.R.drawable.cross,
                    rightIconId = if (uiState.transaction != null) dev.progrover.shmr_finance.core.uicommon.R.drawable.tick
                    else null,
                    onLeftIconClick = { onEvent(EditUIEvent.OnCrossClick) },
                    onRightIconClick = { onEvent(EditUIEvent.OnTickClick) },
                )
            },
        ) {
            if (!uiState.isLoading && uiState.transaction != null) {

                DefaultListItem(
                    modifier = Modifier,
                    title = stringResource(R.string.account),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    additionalText = uiState.transaction.accountName,
                    endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                    onClick = { },
                )

                DefaultListItem(
                    modifier = Modifier,
                    title = stringResource(R.string.category),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    additionalText = uiState.categories.find
                    { it.id == uiState.transaction.categoryId }?.name
                        ?: "",
                    endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                    onClick = { onEvent(EditUIEvent.OnCategoryClick) },
                )
// сделал так, потому что нужно вводить текст при нажатии на область item
                // потом хорошо бы переделать
                Box {
                    DefaultTextField(
                        modifier = Modifier,
                        text = uiState.transaction.amount,
                        dividerVisible = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        focusRequester = amountFocusRequester,
                        hintResId = R.string.point_out,
                        onTextChange = { newAmount ->
                            onEvent(
                                EditUIEvent.OnAmountChange(newAmount)
                            )
                        }
                    )

                    DefaultListItem(
                        modifier = Modifier,
                        title = stringResource(R.string.ammount),
                        verticalTextPadding = AppTheme.paddings.padding8,
                        additionalText = uiState.amountPresented,
                        onClick = { amountFocusRequester.requestFocus() },
                    )
                }

                DefaultListItem(
                    modifier = Modifier,
                    title = stringResource(R.string.date),
                    verticalTextPadding = AppTheme.paddings.padding8,
                    additionalText = uiState.transaction.dateTime.let {
                        if (it != -1L) it.toDatePresentation() else ""
                    },
                    endIconResId = dev.progrover.shmr_finance.core.uicommon.R.drawable.right_arrow,
                    onClick = { onEvent(EditUIEvent.OnDateClick) },
                )
                // сделал так, потому что нужно вводить текст при нажатии на область item
                // потом хорошо бы переделать
                Box {
                    DefaultTextField(
                        modifier = Modifier,
                        text = uiState.transactionTime,
                        dividerVisible = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        focusRequester = timeFocusRequester,
                        hintResId = R.string.point_out,
                        onTextChange = { newTime ->
                            onEvent(
                                EditUIEvent.OnTimeChange(newTime)
                            )
                        }
                    )

                    DefaultListItem(
                        modifier = Modifier,
                        title = stringResource(R.string.time),
                        verticalTextPadding = AppTheme.paddings.padding8,
                        additionalText = uiState.transactionTimePresented,
                        onClick = { timeFocusRequester.requestFocus() },
                    )
                }

                DefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.surface),
                    text = uiState.transaction.comment ?: "",
                    innerVerticalPadding = AppTheme.paddings.padding20,
                    hintResId = R.string.comment,
                    focusRequester = commentFocusRequester,
                    onTextChange = { newText ->
                        onEvent(EditUIEvent.OnCommentChange(newText))
                    }
                )

                if (uiState.actionType == EditVatiant.Edit)
                    DefaultFloatingButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = AppTheme.paddings.padding32,
                                horizontal = AppTheme.paddings.padding16
                            ),
                        text = stringResource(R.string.delete_transaction),
                        cornerRadius = 100.dp,
                        textColor = AppTheme.colors.white,
                        textStyle = AppTheme.typography.labelLarge,
                        innerVerticalPadding = AppTheme.paddings.padding10,
                        backgroundColor = AppTheme.colors.error,
                        onClick = { onEvent(EditUIEvent.OnDeleteClick) },
                    )
            } else ProgressIndicator()
        }
    }

    if (uiState.showDatePicker && uiState.transaction != null)
        DateDialog(
            selectedDate = uiState.dateForPicker,
            onOkClick = { millis ->
                onEvent(EditUIEvent.OnDateChange(millis))
            },
            onCancelClick = {
                onEvent(
                    EditUIEvent.OnDatePickerClose
                )
            },
        )

    if (uiState.showCategoryList)
        ChooseItemDialog(
            modifier = Modifier,
            items = uiState.categories,
            onItemClick = { category ->
                onEvent(EditUIEvent.OnNewCategory(category.id!!))
            },
            onCloseClick = {
                onEvent(EditUIEvent.OnCategoriesListClose)
            },
        )
    if (uiState.alert != null) {
        CustomAlertDialog(
            modifier = Modifier,
            alert = uiState.alert,
            onDismiss = { onEvent(EditUIEvent.OnAlertDialogDone) }
        )
    }
}
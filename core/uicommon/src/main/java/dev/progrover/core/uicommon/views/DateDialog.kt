import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    selectedDate: Long,
    onCancelClick: () -> Unit,
    onOkClick: (Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )

    var clearFlag by remember { mutableStateOf(false) }

    val pickerColors = DatePickerDefaults.colors(
        containerColor = AppTheme.colors.paleGreen,
        titleContentColor = AppTheme.colors.textMain,
        headlineContentColor = AppTheme.colors.textMain,
        weekdayContentColor = AppTheme.colors.textMain,
        subheadContentColor = AppTheme.colors.textMain,
        yearContentColor = AppTheme.colors.textSecondary,
        disabledYearContentColor = AppTheme.colors.textSecondary,
        selectedYearContentColor = AppTheme.colors.textMain,
        selectedYearContainerColor = AppTheme.colors.brightGreen,
        disabledSelectedYearContainerColor = AppTheme.colors.brightGreen,
        currentYearContentColor = AppTheme.colors.textMain,
        dayContentColor = AppTheme.colors.textMain,
        disabledDayContentColor = AppTheme.colors.textSecondary,
        selectedDayContentColor = AppTheme.colors.textMain,
        selectedDayContainerColor = AppTheme.colors.brightGreen,
        todayContentColor = AppTheme.colors.textMain,
        todayDateBorderColor = Color.Transparent,
        dayInSelectionRangeContainerColor = AppTheme.colors.textMain,
        dayInSelectionRangeContentColor = AppTheme.colors.textMain,
        dividerColor = AppTheme.colors.textMain,
        navigationContentColor = AppTheme.colors.textSecondary,
        dateTextFieldColors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.textMain,
            unfocusedTextColor = AppTheme.colors.textMain,
            disabledTextColor = AppTheme.colors.textSecondary,
            errorTextColor = AppTheme.colors.error,

            focusedContainerColor = AppTheme.colors.paleGreen,
            unfocusedContainerColor = AppTheme.colors.paleGreen,
            disabledContainerColor = AppTheme.colors.paleGreen,
            errorContainerColor = AppTheme.colors.paleGreen,

            cursorColor = AppTheme.colors.brightGreen,
            errorCursorColor = AppTheme.colors.error,

            focusedIndicatorColor = AppTheme.colors.brightGreen,
            unfocusedIndicatorColor = AppTheme.colors.textSecondary,
            disabledIndicatorColor = AppTheme.colors.textSecondary,
            errorIndicatorColor = AppTheme.colors.error,

            selectionColors = TextSelectionColors(
                handleColor = AppTheme.colors.brightGreen,
                backgroundColor = AppTheme.colors.brightGreen.copy(alpha = 0.4f)
            )
        )
    )

    LaunchedEffect(clearFlag) {
        datePickerState.selectedDateMillis = selectedDate
    }

    DatePickerDialog(
        modifier = Modifier,
        onDismissRequest = onCancelClick,
        confirmButton = {},
        dismissButton = {},
        colors = pickerColors,
        shape = RoundedCornerShape(15.dp),
        tonalElevation = 0.dp,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Column {
                DatePicker(
                    state = datePickerState,
                    title = null,
                    headline = null,
                    colors = pickerColors,
                    showModeToggle = false
                )

                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.paddings.padding12,
                            vertical = AppTheme.paddings.padding8,
                        ),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding8)
                ) {
                    Text(
                        modifier = Modifier
                            .noRippleClickable { clearFlag = !clearFlag }
                            .padding(
                                horizontal = AppTheme.paddings.padding12,
                                vertical = AppTheme.paddings.padding10
                            ),
                        text = "Clear",
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.labelLarge,
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .noRippleClickable { onCancelClick() }
                            .padding(
                                horizontal = AppTheme.paddings.padding12,
                                vertical = AppTheme.paddings.padding10,
                            ),
                        text = "Cancel",
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.labelLarge,
                    )

                    Text(
                        modifier = Modifier
                            .noRippleClickable { onOkClick(datePickerState.selectedDateMillis!!) }
                            .padding(
                                horizontal = AppTheme.paddings.padding12,
                                vertical = AppTheme.paddings.padding10
                            ),
                        text = "OK",
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.labelLarge.copy(fontWeight = W700),
                    )
                }
            }
        }
    )
}
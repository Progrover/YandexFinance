import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.progrover.core.theme.AppTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochDay()
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onCancel,
        confirmButton = {
            Text(
                modifier = Modifier
                    .clickable {
                    }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                text = "OK",
                color = AppTheme.colors.textMain,
                fontWeight = FontWeight.Bold
            )
        },
        dismissButton = {
            Text(
                modifier = Modifier
                    .clickable(onClick = onCancel)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                text = "Cancel",
                color = AppTheme.colors.textMain
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor = AppTheme.colors.paleGreen,
            titleContentColor = AppTheme.colors.textMain,
            headlineContentColor = AppTheme.colors.textMain,
            weekdayContentColor = AppTheme.colors.textMain,
            subheadContentColor = AppTheme.colors.textMain,
            yearContentColor = AppTheme.colors.textSecondary,
            disabledYearContentColor = AppTheme.colors.textSecondary,
            selectedYearContentColor = AppTheme.colors.textMain,
            selectedYearContainerColor = AppTheme.colors.textMain,
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
        ),
        shape = RoundedCornerShape(15.dp),
        tonalElevation = 0.dp,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            DatePicker(state = datePickerState)
        }
    )
}

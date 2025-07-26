package dev.progrover.core.uicommon.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.conditionally

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(AppTheme.colors.containerHigh),
    enabled: Boolean = true,
    text: String,
    innerVerticalPadding: Dp = 0.dp,
    focusRequester: FocusRequester? = null,
    cursorAlwaysInEnd: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    @StringRes hintResId: Int,
    @DrawableRes startIconId: Int? = null,
    @DrawableRes endIconId: Int? = null,
    dividerVisible: Boolean = true,
    onTextChange: (String) -> Unit,
) {

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length)
            )
        )
    }

    LaunchedEffect(text, cursorAlwaysInEnd) {
        if (text != textFieldValue.text) {
            textFieldValue = if (cursorAlwaysInEnd) {
                TextFieldValue(text, selection = TextRange(text.length))
            } else {
                TextFieldValue(text)
            }
        }
    }

    BasicTextField(
        modifier = modifier
            .conditionally(
                condition = (focusRequester != null),
                trueExtension = {
                    focusRequester(focusRequester!!)
                }),
        value = textFieldValue,
        enabled = enabled,
        singleLine = true,
        textStyle = AppTheme.typography.bodyLarge.copy(color = AppTheme.colors.textSecondary),
        keyboardOptions = keyboardOptions,
        onValueChange = {
            textFieldValue = if (cursorAlwaysInEnd) {
                it.copy(selection = TextRange(it.text.length))
            } else {
                it
            }
            onTextChange(it.text) },
    )
    {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding4),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                if (startIconId != null) {
                    Image(
                        modifier = Modifier
                            .padding(AppTheme.paddings.padding16)
                            .size(AppTheme.sizes.size24),
                        imageVector = ImageVector.vectorResource(startIconId),
                        contentDescription = null,
                    )
                } else Spacer(Modifier.width(AppTheme.paddings.padding16))

                Box(
                    modifier = Modifier
                        .padding(vertical = innerVerticalPadding)
                        .weight(1f)
                ) {

                    if (text.isEmpty()) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(hintResId),
                            maxLines = 1,
                            style = AppTheme.typography.bodyLarge,
                            color = AppTheme.colors.textMain
                        )
                    }

                    it()
                }

                if (endIconId != null) {
                    Image(
                        modifier = Modifier
                            .padding(AppTheme.paddings.padding16)
                            .size(AppTheme.sizes.size24),
                        imageVector = ImageVector.vectorResource(endIconId),
                        contentDescription = null,
                    )
                }
            }

            if (dividerVisible) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = AppTheme.colors.border,
                )
            }
        }
    }
}
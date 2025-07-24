package dev.progrover.feature.auth.impl.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.core.uicommon.views.DefaultTextField
import dev.progrover.shmr_finance.feature.auth.impl.R

@Composable
internal fun PinCodeWindows(
    modifier: Modifier,
    isPrivate: Boolean = false,
    pinCode: String,
    isCorrect: Boolean?,
    focusRequester: FocusRequester,
    onPinCodeChange: (String) -> Unit,
) {

    Box(
        modifier = modifier
            .padding(horizontal = AppTheme.paddings.padding16)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {

        DefaultTextField(
            modifier = Modifier
                .alpha(0f),
            enabled = true,
            text = pinCode,
            focusRequester = focusRequester,
            cursorAlwaysInEnd = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            hintResId = R.string.set_pin,
            dividerVisible = false,
            onTextChange = { newText -> onPinCodeChange(newText) }
        )

        Row(
            modifier = Modifier
                .noRippleClickable({ focusRequester.requestFocus() }),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding12)
        ) {
            PinCodeWindow(
                value = try {
                    pinCode[0]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                isCorrect = isCorrect,
                isPrivate = isPrivate
            )

            PinCodeWindow(
                value = try {
                    pinCode[1]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                isCorrect = isCorrect,
                isPrivate = isPrivate
            )

            PinCodeWindow(
                value = try {
                    pinCode[2]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                isCorrect = isCorrect,
                isPrivate = isPrivate
            )

            PinCodeWindow(
                value = try {
                    pinCode[3]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                isCorrect = isCorrect,
                isPrivate = isPrivate
            )
        }
    }
}

@Composable
private fun PinCodeWindow(
    value: Char?,
    isPrivate: Boolean,
    isCorrect: Boolean?,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(AppTheme.sizes.size8))
            .size(
                width = AppTheme.sizes.size45,
                height = AppTheme.sizes.size60
            )
            .background(
                when (isCorrect) {
                    true -> AppTheme.colors.main.copy(alpha = 0.4f)
                    false -> AppTheme.colors.error.copy(alpha = 0.4f)
                    null -> AppTheme.colors.containerHigh.copy(alpha = (0.4f))
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = if (value == null) " " else {
                if (isPrivate) "*" else value.toString()
            },
            color = AppTheme.colors.textMain,
            style = AppTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}
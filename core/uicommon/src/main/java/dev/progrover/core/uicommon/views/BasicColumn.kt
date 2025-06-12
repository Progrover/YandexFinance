package dev.progrover.core.uicommon.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.progrover.core.theme.AppTheme

/**
 * Функция-заглушка для реализации "подбородка" - отступа от нав. меню
 */
@Composable
fun BasicColumn(
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    toolbar: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {

    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        if (toolbar != null) {
            toolbar()
        }

        content()

        Spacer(
            Modifier.height(
                when (toolbar) {
                    null -> AppTheme.paddings.padding5
                    else -> AppTheme.paddings.padding10
                }
            )
        )
    }
}
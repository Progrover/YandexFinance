package dev.progrover.core.uicommon.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp

@Composable
fun convertSpToDp(spValue: Float): Float {
    val density = LocalDensity.current
    return with(density) { spValue.sp.toDp().value }
}
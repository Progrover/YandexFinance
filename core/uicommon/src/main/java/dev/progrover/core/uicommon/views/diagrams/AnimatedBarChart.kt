import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.progrover.core.base.model.diagrams.BarData
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.views.DefaultFloatingButton
import dev.progrover.shmr_finance.core.uicommon.R
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


@Composable
fun AnimatedBarChart(
    data: List<BarData>,
    modifier: Modifier,
    captionsMode: BarChartCaption,
    diagramHeight: Dp = AppTheme.sizes.size200,
) {
    val scope = rememberCoroutineScope()
    val animatables = remember(data) { data.map { Animatable(0f) } }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var selectedBar by remember { mutableStateOf<BarData?>(null) }
    val textColor = AppTheme.colors.textMain
    val colorsForMultiModeDiagram = listOf(AppTheme.colors.main) + listOf(
        Color(0xFF2AE881),
        Color(0xFF2AC2E8),
        Color(0xFFDB2AE8),
        Color(0xFFE87C2A),
        Color(0xFFDBE82A),
        Color(0xFFE82A2A)
    ).filter { it != AppTheme.colors.main }

    val density = LocalDensity.current
    val minBarHeightPx = with(density) { 4.dp.toPx() }

    LaunchedEffect(canvasSize, data) {
        if (canvasSize.width > 0 && canvasSize.height > 0) {
            data.forEachIndexed { index, item ->
                scope.launch {
                    animatables[index].animateTo(
                        targetValue = item.value,
                        animationSpec = tween(
                            durationMillis = 500,
                            delayMillis = index * 100
                        )
                    )
                }
            }
        }
    }

    Column(modifier = modifier) {

        Box {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(diagramHeight)
                    .onSizeChanged { canvasSize = it }
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val canvasWidth = canvasSize.width.toFloat()
                            val barCount = data.size
                            val barWidth = min(40f, canvasWidth / (barCount * 1.5f))
                            val totalBarsWidth = barCount * barWidth
                            val spacing = (canvasWidth - totalBarsWidth) / (barCount + 1)

                            var x = spacing
                            data.forEachIndexed { index, bar ->
                                val left = x
                                val right = left + barWidth
                                if (offset.x in left..right) {
                                    selectedBar = bar
                                }
                                x += barWidth + spacing
                            }
                        }
                    }
            ) {
                val captionHeightPx = with(density) { 24.dp.toPx() }
                val canvasWidth = size.width
                val canvasHeight = size.height
                val barCount = data.size
                val barWidth = min(40f, canvasWidth / (barCount * 1.5f))
                val spacing = (canvasWidth - barWidth * barCount) / (barCount + 1)

                val maxAbsValue = data.maxOf { abs(it.value) }.coerceAtLeast(1f)
                val bottomY = canvasHeight - captionHeightPx

                var x = spacing
                data.forEachIndexed { index, bar ->
                    val animatedValue = animatables[index].value
                    val absHeight = (abs(animatedValue) / maxAbsValue) * bottomY
                    val height = max(minBarHeightPx, absHeight)

                    val isPositive = animatedValue >= 0f
                    val color = when (captionsMode) {
                        BarChartCaption.FirstAndLast -> {
                            if (isPositive) Color(0xFF00D37F) else Color(0xFFFF5E00)
                        }

                        BarChartCaption.All -> {
                            colorsForMultiModeDiagram[index]
                        }
                    }

                    val topLeft = Offset(x, bottomY - height)

                    drawRoundRect(
                        color = color,
                        topLeft = topLeft,
                        size = Size(barWidth, height),
                        cornerRadius = CornerRadius(6f, 6f)
                    )

                    drawRoundRect(
                        color = color,
                        topLeft = topLeft,
                        size = Size(barWidth, height),
                        cornerRadius = CornerRadius(6f, 6f)
                    )

                    if (
                        captionsMode == BarChartCaption.All ||
                        (captionsMode == BarChartCaption.FirstAndLast && (index == 0 || index == data.lastIndex))
                    ) {
                        drawContext.canvas.nativeCanvas.drawText(
                            bar.caption,
                            x + barWidth / 2,
                            canvasHeight - 4f,
                            android.graphics.Paint().apply {
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = 28f
                                typeface = android.graphics.Typeface.create(
                                    android.graphics.Typeface.DEFAULT,
                                    android.graphics.Typeface.NORMAL
                                )
                            }
                        )
                    }

                    x += barWidth + spacing
                }
            }

            selectedBar?.let { bar ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(diagramHeight),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(AppTheme.sizes.size16)
                            .wrapContentSize(),
                        elevation = CardDefaults.cardElevation(AppTheme.sizes.size5)
                    ) {
                        Column(
                            modifier = Modifier.padding(AppTheme.paddings.padding16),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(bottom = AppTheme.paddings.padding8),
                                text = stringResource(R.string.description),
                                fontWeight = FontWeight.Bold
                            )

                            Text(bar.description)

                            DefaultFloatingButton(
                                modifier = Modifier
                                    .padding(top = AppTheme.paddings.padding8),
                                text = stringResource(R.string.close),
                                cornerRadius = AppTheme.sizes.size5,
                                innerVerticalPadding = AppTheme.paddings.padding6,
                                textColor = AppTheme.colors.textMain,
                                backgroundColor = AppTheme.colors.main,
                                onClick = { selectedBar = null }
                            )
                        }
                    }
                }
            }
        }

        if (captionsMode == BarChartCaption.All) {
            Column(
                modifier = Modifier
                    .padding(top = AppTheme.paddings.padding16),
                verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding6)
            ) {
                for (itemIndex in 0..data.lastIndex) {
                    Row {

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = AppTheme.paddings.padding16)
                                .size(AppTheme.sizes.size10)
                                .clip(CircleShape)
                                .background(colorsForMultiModeDiagram[itemIndex])
                        )

                        Text(
                            text = data[itemIndex].legend.ifBlank
                            { stringResource(R.string.another) },
                            color = textColor,
                            style = AppTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

enum class BarChartCaption {
    FirstAndLast,
    All,
}
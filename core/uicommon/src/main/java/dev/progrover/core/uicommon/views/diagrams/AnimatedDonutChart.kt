package dev.progrover.core.uicommon.views.diagrams

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.progrover.core.base.model.diagrams.BarData
import dev.progrover.core.theme.AppTheme
import dev.progrover.shmr_finance.core.uicommon.R

@Composable
fun AnimatedDonutChart(
    modifier: Modifier,
    data: List<BarData>,
    ringThickness: Dp = 40.dp,
    diagramSize: Dp = 170.dp
) {
    val defaultLegend = stringResource(R.string.another)
    val mainColor = AppTheme.colors.main
    val sortedData = remember(data, defaultLegend) {
        data.sortedByDescending { it.value }
            .map {
                if (it.legend.isBlank()) it.copy(legend = defaultLegend) else it
            }
    }

    val minSweepAngle = 4f
    val totalMinSweep = minSweepAngle * sortedData.size
    val remainingSweep = 360f - totalMinSweep

    val adjustedAngles = remember(sortedData) {
        val totalValue = sortedData.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f)
        sortedData.map { bar ->
            minSweepAngle + (bar.value / totalValue) * remainingSweep
        }
    }

    val sweepAnimations = remember(adjustedAngles) {
        adjustedAngles.map { Animatable(0f) }
    }

    LaunchedEffect(adjustedAngles) {
        sweepAnimations.forEach { it.snapTo(0f) }
        adjustedAngles.forEachIndexed { index, targetAngle ->
            sweepAnimations[index].animateTo(
                targetValue = targetAngle,
                animationSpec = tween(durationMillis = 400)
            )
        }
    }

    val colorsForMultiModeDiagram = listOf(mainColor) + listOf(
        Color(0xFF2AE881),
        Color(0xFF2AC2E8),
        Color(0xFFDB2AE8),
        Color(0xFFE87C2A),
        Color(0xFFDBE82A),
    ).filter { it != mainColor }

    val stroke = with(LocalDensity.current) { ringThickness.toPx() }

    Column(modifier = modifier) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = modifier
                    .size(diagramSize)
            ) {
                val diameter = size.minDimension
                val radius = diameter / 2
                val rectSize = Size(diameter, diameter)
                val topLeft = Offset(center.x - radius, center.y - radius)

                var startAngle = -90f
                sweepAnimations.forEachIndexed { index, animatedAngle ->
                    drawArc(
                        color = colorsForMultiModeDiagram.getOrElse(index) { Color.Gray },
                        startAngle = startAngle,
                        sweepAngle = animatedAngle.value,
                        useCenter = false,
                        style = Stroke(width = stroke, cap = StrokeCap.Butt),
                        size = rectSize,
                        topLeft = topLeft
                    )
                    startAngle += animatedAngle.value
                }
            }
        }


        Column(
            modifier = Modifier.padding(top = AppTheme.paddings.padding16),
            verticalArrangement = Arrangement.spacedBy(AppTheme.paddings.padding6)
        ) {
            sortedData.forEachIndexed { index, item ->
                Row {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = AppTheme.paddings.padding16)
                            .size(AppTheme.sizes.size10)
                            .clip(CircleShape)
                            .background(colorsForMultiModeDiagram.getOrElse(index) { Color.Gray })
                    )

                    val percent = if (item.value.toInt() == 0) "<1" else item.value.toInt()
                    Text(
                        text = "${item.legend}: ${percent}%",
                        color = AppTheme.colors.textMain,
                        style = AppTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
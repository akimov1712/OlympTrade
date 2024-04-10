package ru.topbun.olymptrade.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ru.topbun.olymptrade.data.Bar
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS = 20
private const val MAX_VISIBLE_BARS = 1000

@Composable
fun Terminal(bars: List<Bar>) {
    var visibleBarsCount by rememberSaveable {
        mutableStateOf(100)
    }
    var takeBarsFrom by rememberSaveable {
        mutableStateOf(0)
    }
    var displacement by rememberSaveable {
        mutableStateOf(0f)
    }
    val transformableState = TransformableState { zoomChange, panChange, _ ->
        visibleBarsCount = (visibleBarsCount / zoomChange).roundToInt()
            .coerceIn(MIN_VISIBLE_BARS, MAX_VISIBLE_BARS)
        displacement = panChange.getDistance()
    }
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .transformable(transformableState)
    ) {
        val visibleBars = bars.slice(takeBarsFrom..takeBarsFrom+visibleBarsCount)
        val widthBar = size.width / visibleBars.size
        val max = bars.maxBy { it.high }.high
        val min = bars.minBy { it.low }.low
        val pixelOnePoint = size.height / (max - min)
        takeBarsFrom += (displacement / widthBar).roundToInt()
        visibleBars.forEachIndexed { index, bar ->
            val offsetX = size.width - widthBar * index
            val startY = size.height - (bar.low - min) * pixelOnePoint
            val endY = size.height - (bar.high - min) * pixelOnePoint
            drawLine(
                color = Color.White,
                start = Offset(offsetX,startY),
                end = Offset(offsetX,endY),
                strokeWidth = widthBar - 2
            )
        }
    }
}
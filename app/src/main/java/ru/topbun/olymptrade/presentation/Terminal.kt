package ru.topbun.olymptrade.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import ru.topbun.olymptrade.data.Bar
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS = 20

@Composable
fun Terminal(bars: List<Bar>) {

    var terminalState by rememberSaveable {
        mutableStateOf(TerminalState(bars))
    }

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        with(terminalState){
            val visibleBarsCount = (visibleBarsCount / zoomChange).roundToInt()
                .coerceIn(MIN_VISIBLE_BARS, bars.size)

            val scrolledBy = (scrolledBy + panChange.x)
                .coerceAtLeast(0f)
                .coerceAtMost(bars.size * barWidth - terminalWidth)
            terminalState = terminalState.copy(
                visibleBarsCount = visibleBarsCount,
                scrolledBy = scrolledBy
            )
        }

    }
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .transformable(transformableState)
        .onSizeChanged {
            terminalState = terminalState.copy(terminalWidth = it.width.toFloat())
        }
    ) {
        val max = terminalState.visibleBars.maxBy { it.high }.high
        val min = terminalState.visibleBars.minBy { it.low }.low
        val pixelOnePoint = size.height / (max - min)
        translate(left = terminalState.scrolledBy) {
            bars.forEachIndexed { index, bar ->
                val offsetX = size.width - terminalState.barWidth * index
                val startY = size.height - (bar.low - min) * pixelOnePoint
                val endY = size.height - (bar.high - min) * pixelOnePoint
                val startShadowY = size.height - (bar.open - min) * pixelOnePoint
                val endShadowY = size.height - (bar.close - min) * pixelOnePoint
                val color = if (bar.open > bar.close) Color.Green else Color.Red
                drawLine(
                    color = color,
                    start = Offset(offsetX,startY),
                    end = Offset(offsetX,endY),
                    strokeWidth = terminalState.barWidth / 7
                )
                drawLine(
                    color = color,
                    start = Offset(offsetX,startShadowY),
                    end = Offset(offsetX,endShadowY),
                    strokeWidth = terminalState.barWidth - 5f
                )
            }
        }
    }
}
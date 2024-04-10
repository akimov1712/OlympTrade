package ru.topbun.olymptrade.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ru.topbun.olymptrade.data.Bar

@Composable
fun Terminal(bars: List<Bar>) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        val path = Path()
        val widthBar = size.width / bars.size
        val max = bars.maxBy { it.high }.high
        val min = bars.minBy { it.low }.low
        val pixelOnePoint = size.height / (max - min)
        bars.forEachIndexed { index, bar ->
            val offsetX = widthBar * index
            val startY = size.height - (bar.low - min) * pixelOnePoint
            val endY = size.height - (bar.high - min) * pixelOnePoint
            if (index == 0){
                path.moveTo(offsetX, startY)
            } else {
                path.lineTo(offsetX,startY)
            }
//            drawLine(
//                color = Color.White,
//                start = Offset(offsetX,startY),
//                end = Offset(offsetX,endY),
//                strokeWidth = 1.5f
//            )
        }
        drawPath(path = path, Color.White, style = Stroke(1.dp.toPx()))
    }
}
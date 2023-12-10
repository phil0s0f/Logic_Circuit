package com.example.logiccircuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logiccircuit.ui.theme.LogicCircuitTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogicCircuitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CanvasPreview()
                }
            }
        }
    }
}

@Composable
fun CanvasPreview() {
    var andElementCount by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)

        )
        {
            Column(
                modifier = Modifier
                    //TODO: Добавить рамку
                    .size(100.dp, 500.dp)//TODO: Изменить размер по высоте
                    .padding(15.dp)
                    .border(1.dp, Color.Gray)

            )
            {
                Button(
                    onClick = {
                        /*TODO*/
                        andElementCount++
                    }
                )
                {
                    Text(text = "and")
                }
            }

        }

        repeat(andElementCount) {

                DrawAnd()
        }
    }
}

@Composable
fun DrawAnd() {
    var offsetX by remember { mutableStateOf(500f) }
    var offsetY by remember { mutableStateOf(200f) }


    val rectangleWidth = 70.dp
    val rectangleHeight = 100.dp

    val strokeWidth = 2.dp

    val textToDraw = "&"
    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(rectangleWidth, rectangleHeight)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    )
    {
        Text(
            text = textToDraw,
            fontSize = 15.sp,
            modifier = Modifier.offset {
                IntOffset(
                    (rectangleWidth.toPx() / 2).roundToInt(),
                    (rectangleWidth.toPx() / 2).roundToInt()
                )
            }
        )
        var index = 0
        Canvas(
            modifier = Modifier
//            .offset(300.dp, 100.dp)
                .pointerInput(Unit) {//TODO
                    detectTapGestures(
                        onTap = {
                            // When the user taps on the Canvas, you can
                            // check if the tap offset is in one of the
                            // tracked Rects.
                                index++
                        }
                    )
                }
                .border(5.dp,Color.Red)
        ) {
            drawRoundRect(
                color = Color.Black,
                size = Size(rectangleWidth.toPx(), rectangleHeight.toPx()),
                cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx()),
                style = Stroke(strokeWidth.toPx())
            )
            //Рисуем левую верхнюю линию
            drawLine(
                color = Color.Black,
                start = Offset(center.x, center.y + 40f),
                end = Offset(center.x - 40f, center.y + 40f),
                strokeWidth = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
            drawPoints(
                points = listOf(Offset(center.x - 40f, center.y + 40f)),
                pointMode = PointMode.Points,
                cap = StrokeCap.Round,
                color = Color.Red,
                strokeWidth = 25f
            )

            //Рисуем левую нижнию линию
            drawLine(
                color = Color.Black,
                start = Offset(center.x, rectangleHeight.toPx() - 40f),
                end = Offset(center.x - 40f, rectangleHeight.toPx() - 40f),
                strokeWidth = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
            drawPoints(
                points = listOf(Offset(center.x - 40f, rectangleHeight.toPx() - 40f)),
                pointMode = PointMode.Points,
                cap = StrokeCap.Round,
                color = Color.Red,
                strokeWidth = 25f
            )

            // Рисуем линию справа
            drawLine(
                color = Color.Black,
                start = Offset(rectangleWidth.toPx(), rectangleHeight.toPx() / 2),
                end = Offset(rectangleWidth.toPx() + 40f, rectangleHeight.toPx() / 2),
                strokeWidth = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
            drawPoints(
                points = listOf(Offset(rectangleWidth.toPx() + 40f, rectangleHeight.toPx() / 2)),
                pointMode = PointMode.Points,
                cap = StrokeCap.Round,
                color = Color.Red,
                strokeWidth = 25f
            )

        }
    }
}


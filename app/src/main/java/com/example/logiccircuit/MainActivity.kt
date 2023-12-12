package com.example.logiccircuit

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logiccircuit.ui.theme.LogicCircuitTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    enum class BUTTON_POINT_CLASS {
        ENTER1,
        ENTER2,
        EXIT,
    }

    enum class COMPONENT_TYPE_CLASS {
        AND,
        OR,
        INV,
    }

    interface IButtonPoint {
        val isActive: Boolean;
        val layout_x: MutableState<Float>;
        val layout_y: MutableState<Float>;
        val connected_to: IButtonPoint?;
//        val type: BUTTON_POINT_CLASS
    }

    data class ButtonPoint(
        override val isActive: Boolean,
        override val layout_x: MutableState<Float>,
        override val layout_y: MutableState<Float>,
        override val connected_to: IButtonPoint?,
//        override val type: BUTTON_POINT_CLASS
    ) : IButtonPoint

    interface IComponent {
        val layout_x: MutableState<Float>;
        val layout_y: MutableState<Float>;
        val enter1_button: ButtonPoint
        val enter2_button: ButtonPoint
        val exit_button: ButtonPoint
        val component_type: COMPONENT_TYPE_CLASS
    }

    data class MyComponent(
        override val layout_x: MutableState<Float>,
        override val layout_y: MutableState<Float>,
        override val enter1_button: ButtonPoint,
        override val enter2_button: ButtonPoint,
        override val exit_button: ButtonPoint,
        override val component_type: COMPONENT_TYPE_CLASS

    ) : IComponent

    interface ILine {
        val start_button: ButtonPoint
        val end_button: ButtonPoint
    }

    data class LineBetweenComponent(
        override val start_button: ButtonPoint,
        override val end_button: ButtonPoint

    ) : ILine

    val componentList = mutableListOf<IComponent>()
    val lineList = mutableListOf<ILine>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //TODO сделать их mutablelist

            var activePoint1: MutableState<ButtonPoint?> = remember { mutableStateOf(null) }
            var activePoint2: MutableState<ButtonPoint?> = remember { mutableStateOf(null) }
            LogicCircuitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CanvasPreview(activePoint1,activePoint2)
                }
            }
        }
    }

    @Composable
    fun CanvasPreview(
        activePoint1: MutableState<ButtonPoint?>,
        activePoint2: MutableState<ButtonPoint?>

    ) {
        var andElementCount by remember { mutableStateOf(0) }
        var lineCount = remember { mutableStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        {

            Canvas(modifier = Modifier.fillMaxSize()) {
                repeat(lineCount.value) { index ->//TODO
                    drawLine(
                        color = Color.Black,
                        start = Offset(
                            lineList[index].start_button.layout_x.value,
                            lineList[index].start_button.layout_y.value,
                        ),
                        end = Offset(
                            lineList[index].end_button.layout_x.value,
                            lineList[index].end_button.layout_y.value,
                        ),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    activePoint1.value = null
                    activePoint2.value = null
                }
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)

            )
            {
                Column(
                    modifier = Modifier
                        .size(110.dp, 500.dp)//TODO: Изменить размер по высоте
                        .padding(15.dp)
                        .border(1.dp, Color.Gray)

                )
                {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            componentList.add(
                                MyComponent(
                                    mutableStateOf(500f),
                                    mutableStateOf(200f),
                                    ButtonPoint(
                                        false,
                                        mutableStateOf(435f),
                                        mutableStateOf(219f),
                                        null
                                    ),
                                    ButtonPoint(
                                        false,
                                        mutableStateOf(435f),
                                        mutableStateOf(413f),
                                        null
                                    ),
                                    ButtonPoint(
                                        false,
                                        mutableStateOf(717f),
                                        mutableStateOf(316f),
                                        null
                                    ),
                                    COMPONENT_TYPE_CLASS.AND
                                )
                            )
                            andElementCount++
                        }
                    )
                    {
                        Text(text = "AND")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            componentList.add(
                                MyComponent(
                                    mutableStateOf(500f),
                                    mutableStateOf(200f),
                                    ButtonPoint(
                                        false,
                                        mutableStateOf(435f),
                                        mutableStateOf(219f),
                                        null
                                    ),
                                    ButtonPoint(
                                        false,
                                        mutableStateOf(435f),
                                        mutableStateOf(413f),
                                        null
                                    ),
                                    ButtonPoint(
                                        false,
                                        mutableStateOf(717f),
                                        mutableStateOf(316f),
                                        null
                                    ),
                                    COMPONENT_TYPE_CLASS.OR
                                )
                            )
                            andElementCount++
                        }
                    )
                    {
                        Text(text = "OR")
                    }
                }
            }

            repeat(andElementCount) { index ->
                DrawAnd(componentList.get(index), lineCount, activePoint1, activePoint2)
//                Log.e("ksl", "kal")
//                DrawAnd()
            }
        }
    }

    @Composable
    fun DrawAnd(
        component: IComponent,
        lineCount: MutableState<Int>,
        activePoint1: MutableState<ButtonPoint?>,
        activePoint2: MutableState<ButtonPoint?>
    ) {


        val rectangleWidth = 70.dp
        val rectangleHeight = 100.dp

        val strokeWidth = 2.dp
        var textToDraw = ""

        when (component.component_type.name) {
            "AND" -> textToDraw = "&"
            "OR" -> textToDraw = "1"
            "INV" -> textToDraw = "1"
        }

        Box(
            Modifier
                .offset {
                    IntOffset(
                        component.layout_x.value.roundToInt(),
                        component.layout_y.value.roundToInt()
                    )
                }
                .size(rectangleWidth, rectangleHeight)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        Log.e(
                            "cordComp",
                            "${component.layout_x.value}, ${component.layout_y.value}"
                        )
                        Log.e(
                            "cordB",
                            "${component.enter1_button.layout_x.value}, ${component.enter1_button.layout_y.value}"
                        )
                        component.layout_x.value += dragAmount.x
                        component.layout_y.value += dragAmount.y
                        component.enter1_button.layout_x.value += dragAmount.x
                        component.enter1_button.layout_y.value += dragAmount.y
                        component.enter2_button.layout_x.value += dragAmount.x
                        component.enter2_button.layout_y.value += dragAmount.y
                        component.exit_button.layout_x.value += dragAmount.x
                        component.exit_button.layout_y.value += dragAmount.y
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
//            drawPoints(
//                points = listOf(Offset(center.x - 40f, center.y + 40f)),
//                pointMode = PointMode.Points,
//                cap = StrokeCap.Round,
//                color = Color.Red,
//                strokeWidth = 25f
//            )

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
                    points = listOf(
                        Offset(
                            rectangleWidth.toPx() + 40f,
                            rectangleHeight.toPx() / 2
                        )
                    ),
                    pointMode = PointMode.Points,
                    cap = StrokeCap.Round,
                    color = Color.Red,
                    strokeWidth = 25f
                )

            }


        }
        Button(
            onClick = {
                Log.e("test", "WORK")
                if (activePoint1.value == null) {
                    activePoint1.value = component.enter1_button
                } else if (activePoint2.value == null) {
                    activePoint2.value = component.enter1_button
                }
                if (activePoint1.value != null && activePoint2.value != null) {
                    lineList.add(LineBetweenComponent(activePoint1.value!!, activePoint2.value!!))
                    lineCount.value++
                }
            },
            modifier = Modifier
                .size(15.dp)
                .offset {
                    IntOffset(
                        component.enter1_button.layout_x.value.roundToInt(),
                        component.enter1_button.layout_y.value.roundToInt()
                    )
                }
        )
        {
        }

        Button(
            onClick = {
                Log.e("test", "WORK")
                if (activePoint1.value == null) {
                    activePoint1.value = component.enter2_button
                } else if (activePoint2.value == null) {
                    activePoint2.value = component.enter2_button
                }
                if (activePoint1.value != null && activePoint2.value != null) {
                    lineList.add(LineBetweenComponent(activePoint1.value!!, activePoint2.value!!))
                    lineCount.value++
                }
            },
            modifier = Modifier
                .size(15.dp)
                .offset {
                    IntOffset(
                        component.enter2_button.layout_x.value.roundToInt(),
                        component.enter2_button.layout_y.value.roundToInt()
                    )
                }
        )
        {
        }
        Button(
            onClick = {
                Log.e("test", "WORK")
                if (activePoint1.value == null) {
                    activePoint1.value = component.exit_button
                } else if (activePoint2.value == null) {
                    activePoint2.value = component.exit_button
                }
                if (activePoint1.value != null && activePoint2.value != null) {
                    lineList.add(LineBetweenComponent(activePoint1.value!!, activePoint2.value!!))
                    lineCount.value++
                }
            },
            modifier = Modifier
                .size(15.dp)
                .offset {
                    IntOffset(
                        component.exit_button.layout_x.value.roundToInt(),
                        component.exit_button.layout_y.value.roundToInt()
                    )
                }
        )
        {
        }
    }

}

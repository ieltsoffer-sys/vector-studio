package com.vectorstudio.app.ui.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin

enum class ShapeKind {
    RECTANGLE,
    CIRCLE,
    STAR,
}

data class EditableShape(
    val id: Long,
    val kind: ShapeKind,
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    var rotation: Float = 0f,
    var fill: Color = Color(0xFF6EE7B7.toInt()),
    var stroke: Color = Color(0xFF1F2937.toInt()),
    var strokeWidth: Float = 3f,
)

fun buildStarPath(cx: Float, cy: Float, outerRadius: Float, innerRadius: Float, tips: Int = 5): Path {
    val totalPoints = tips * 2
    val path = Path()
    for (index in 0..totalPoints) {
        val angle = -Math.PI / 2 + (index.toDouble() / totalPoints) * Math.PI * 2.0
        val radius = if (index % 2 == 0) outerRadius else innerRadius
        val x = cx + cos(angle) * radius
        val y = cy + sin(angle) * radius
        if (index == 0) {
            path.moveTo(x.toFloat(), y.toFloat())
        } else {
            path.lineTo(x.toFloat(), y.toFloat())
        }
    }
    path.close()
    return path
}

fun Offset.clampToCanvas(minX: Float, minY: Float, maxX: Float, maxY: Float): Offset {
    return Offset(
        x = x.coerceIn(minX, maxX),
        y = y.coerceIn(minY, maxY),
    )
}

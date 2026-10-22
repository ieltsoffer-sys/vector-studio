package com.vectorstudio.app.ui.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

class EditorViewModel {
    val shapes = mutableStateListOf<EditableShape>()
    var selectedShapeId by mutableStateOf<Long?>(null)

    init {
        shapes.addAll(
            listOf(
                EditableShape(
                    id = 1L,
                    kind = ShapeKind.RECTANGLE,
                    x = 140f,
                    y = 160f,
                    width = 240f,
                    height = 180f,
                    fill = Color(0xFF5B8CFF.toInt()),
                    stroke = Color(0xFF0F172A.toInt()),
                    strokeWidth = 4f,
                ),
                EditableShape(
                    id = 2L,
                    kind = ShapeKind.CIRCLE,
                    x = 420f,
                    y = 240f,
                    width = 150f,
                    height = 150f,
                    fill = Color(0xFF7EE7C7.toInt()),
                    stroke = Color(0xFF0F172A.toInt()),
                    strokeWidth = 4f,
                ),
                EditableShape(
                    id = 3L,
                    kind = ShapeKind.STAR,
                    x = 630f,
                    y = 190f,
                    width = 170f,
                    height = 170f,
                    fill = Color(0xFFF9A8D4.toInt()),
                    stroke = Color(0xFF0F172A.toInt()),
                    strokeWidth = 4f,
                ),
            )
        )
        selectedShapeId = 1L
    }

    fun selectShape(shapeId: Long) {
        selectedShapeId = shapeId
    }

    fun addShape(shapeKind: ShapeKind) {
        val nextId = (shapes.maxOfOrNull { it.id } ?: 0L) + 1L
        val newShape = EditableShape(
            id = nextId,
            kind = shapeKind,
            x = 90f + (shapes.size * 30).toFloat(),
            y = 90f + (shapes.size * 24).toFloat(),
            width = when (shapeKind) {
                ShapeKind.RECTANGLE -> 200f
                ShapeKind.CIRCLE -> 170f
                ShapeKind.STAR -> 180f
            },
            height = when (shapeKind) {
                ShapeKind.RECTANGLE -> 140f
                ShapeKind.CIRCLE -> 170f
                ShapeKind.STAR -> 180f
            },
            fill = when (shapeKind) {
                ShapeKind.RECTANGLE -> Color(0xFF818CF8.toInt())
                ShapeKind.CIRCLE -> Color(0xFF67E8F9.toInt())
                ShapeKind.STAR -> Color(0xFFFACF5A.toInt())
            },
            stroke = Color(0xFF111827.toInt()),
            strokeWidth = 3f,
        )
        shapes.add(newShape)
        selectedShapeId = newShape.id
    }

    fun deleteSelected() {
        val target = selectedShapeId ?: return
        shapes.removeIf { it.id == target }
        selectedShapeId = shapes.lastOrNull()?.id
    }

    fun moveSelected(delta: Offset) {
        val target = selectedShapeId ?: return
        val shape = shapes.firstOrNull { it.id == target } ?: return
        shape.x = (shape.x + delta.x).coerceAtLeast(10f)
        shape.y = (shape.y + delta.y).coerceAtLeast(10f)
    }

    fun resizeSelected(delta: Offset) {
        val target = selectedShapeId ?: return
        val shape = shapes.firstOrNull { it.id == target } ?: return
        shape.width = (shape.width + delta.x).coerceAtLeast(60f)
        shape.height = (shape.height + delta.y).coerceAtLeast(60f)
    }

    fun applyFill(color: Color) {
        val target = selectedShapeId ?: return
        val shape = shapes.firstOrNull { it.id == target } ?: return
        shape.fill = color
    }

    fun applyStroke(color: Color) {
        val target = selectedShapeId ?: return
        val shape = shapes.firstOrNull { it.id == target } ?: return
        shape.stroke = color
    }

    fun applyStrokeWidth(value: Float) {
        val target = selectedShapeId ?: return
        val shape = shapes.firstOrNull { it.id == target } ?: return
        shape.strokeWidth = value
    }

    fun applyRotation(value: Float) {
        val target = selectedShapeId ?: return
        val shape = shapes.firstOrNull { it.id == target } ?: return
        shape.rotation = value
    }

    fun exportSvg(): String {
        val body = shapes.joinToString(separator = "\n") { shape ->
            when (shape.kind) {
                ShapeKind.RECTANGLE -> {
                    val fill = toHex(shape.fill)
                    val stroke = toHex(shape.stroke)
                    "  <rect x=\"${shape.x.toInt()}\" y=\"${shape.y.toInt()}\" width=\"${shape.width.toInt()}\" height=\"${shape.height.toInt()}\" rx=\"28\" fill=\"$fill\" stroke=\"$stroke\" stroke-width=\"${shape.strokeWidth}\" transform=\"rotate(${shape.rotation} ${shape.x + shape.width / 2} ${shape.y + shape.height / 2})\" />"
                }
                ShapeKind.CIRCLE -> {
                    val fill = toHex(shape.fill)
                    val stroke = toHex(shape.stroke)
                    val cx = shape.x + shape.width / 2f
                    val cy = shape.y + shape.height / 2f
                    val r = (shape.width.coerceAtMost(shape.height) / 2f)
                    "  <circle cx=\"${cx.toInt()}\" cy=\"${cy.toInt()}\" r=\"${r.toInt()}\" fill=\"$fill\" stroke=\"$stroke\" stroke-width=\"${shape.strokeWidth}\" />"
                }
                ShapeKind.STAR -> {
                    val fill = toHex(shape.fill)
                    val stroke = toHex(shape.stroke)
                    val cx = shape.x + shape.width / 2f
                    val cy = shape.y + shape.height / 2f
                    val outerRadius = shape.width / 2f
                    val innerRadius = shape.width / 3.5f
                    val points = buildStarPoints(cx, cy, outerRadius, innerRadius)
                    val pathData = points.joinToString(separator = " ") { point ->
                        "${point.x.toInt()},${point.y.toInt()}"
                    }
                    "  <polygon points=\"$pathData\" fill=\"$fill\" stroke=\"$stroke\" stroke-width=\"${shape.strokeWidth}\" />"
                }
            }
        }

        return """
<svg xmlns="http://www.w3.org/2000/svg" width="1400" height="900" viewBox="0 0 1400 900">
  <rect width="100%" height="100%" fill="white" />
$body
</svg>
""".trimIndent()
    }

    private fun buildStarPoints(
        cx: Float,
        cy: Float,
        outerRadius: Float,
        innerRadius: Float,
        tips: Int = 5,
    ): List<Offset> {
        val points = mutableListOf<Offset>()
        val totalPoints = tips * 2
        for (index in 0..totalPoints) {
            val angle = -Math.PI / 2 + (index.toDouble() / totalPoints) * Math.PI * 2.0
            val radius = if (index % 2 == 0) outerRadius else innerRadius
            val x = cx + cos(angle) * radius
            val y = cy + sin(angle) * radius
            points.add(Offset(x.toFloat(), y.toFloat()))
        }
        return points
    }

    private fun toHex(color: Color): String {
        val alpha = (color.alpha * 255).toInt()
        val red = (color.red * 255).toInt()
        val green = (color.green * 255).toInt()
        val blue = (color.blue * 255).toInt()
        return String.format("#%02X%02X%02X", red, green, blue)
    }
}

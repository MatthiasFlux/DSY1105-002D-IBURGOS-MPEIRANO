package com.example.appmodoguardian_grupo1.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

private val TextMutedDefault = Color(0xFF94A3B8)

@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier.size(20.dp)) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.18f

        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 200f,
            sweepAngle = 115f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 120f,
            sweepAngle = 85f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 30f,
            sweepAngle = 95f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -45f,
            sweepAngle = 75f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawLine(
            color = Color(0xFF4285F4),
            start = Offset(w * 0.45f, h * 0.5f),
            end = Offset(w * 0.95f, h * 0.5f),
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun EyeIcon(
    visible: Boolean,
    tint: Color = TextMutedDefault,
    modifier: Modifier = Modifier.size(20.dp)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeW = 2.dp.toPx()

        drawArc(
            color = tint,
            startAngle = 205f,
            sweepAngle = 130f,
            useCenter = false,
            style = Stroke(width = strokeW)
        )
        drawArc(
            color = tint,
            startAngle = 25f,
            sweepAngle = 130f,
            useCenter = false,
            style = Stroke(width = strokeW)
        )
        drawCircle(
            color = tint,
            radius = w * 0.16f,
            center = Offset(w / 2, h / 2)
        )

        if (!visible) {
            drawLine(
                color = tint,
                start = Offset(w * 0.15f, h * 0.85f),
                end = Offset(w * 0.85f, h * 0.15f),
                strokeWidth = strokeW
            )
        }
    }
}
package com.mapbox.maps.extension.compose.internal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * A place holder to show the Map in Android Studio Preview.
 */
@Composable
@JvmSynthetic
internal fun MapPreviewPlaceHolder(
  modifier: Modifier,
  placeholderColor: Color = Color(0xFFD2D2D9),
  strokeWidthInDp: Int = 5,
  strokeColor: Color = Color.Blue
) {
  Canvas(modifier = modifier.background(placeholderColor)) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    drawRect(
      color = strokeColor,
      size = size,
      style = Stroke(width = strokeWidthInDp.dp.toPx())
    )
    drawLine(
      start = Offset(x = canvasWidth, y = 0f),
      end = Offset(x = 0f, y = canvasHeight),
      strokeWidth = strokeWidthInDp.dp.toPx(),
      color = strokeColor
    )
    drawLine(
      start = Offset(x = 0f, y = 0f),
      end = Offset(x = canvasWidth, y = canvasHeight),
      strokeWidth = strokeWidthInDp.dp.toPx(),
      color = strokeColor
    )
  }
}
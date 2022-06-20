package com.mapbox.maps.extension.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettingsInterface

@Composable
fun MapboxMap(
  modifier: Modifier = Modifier,
  mapInitOptions: MapInitOptions = MapInitOptions(LocalContext.current),
  styleExtension: StyleContract.StyleExtension? = null,
  update: (MapView) -> Unit = NoOpUpdate
) {
  if (LocalInspectionMode.current) {
    PlaceHolder(modifier)
  } else {
    AndroidView(
      factory = { context ->
        MapView(context, mapInitOptions)
      },
      modifier = modifier,
    ) { mapView ->
      mapView.apply(update)
      styleExtension?.let {
        mapView.getMapboxMap().loadStyle(it)
      }
    }
  }
}

@Composable
internal fun PlaceHolder(
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

fun GesturesSettingsInterface.applySettings(gesturesSettings: GesturesSettings) {
  this.updateSettings {
    rotateEnabled = gesturesSettings.rotateEnabled
    pinchToZoomEnabled = gesturesSettings.pinchToZoomEnabled
    scrollEnabled = gesturesSettings.scrollEnabled
    simultaneousRotateAndPinchToZoomEnabled =
      gesturesSettings.simultaneousRotateAndPinchToZoomEnabled
    pitchEnabled = gesturesSettings.pitchEnabled
    scrollMode = gesturesSettings.scrollMode
    doubleTapToZoomInEnabled = gesturesSettings.doubleTapToZoomInEnabled
    doubleTouchToZoomOutEnabled = gesturesSettings.doubleTouchToZoomOutEnabled
    quickZoomEnabled = gesturesSettings.quickZoomEnabled
    focalPoint = gesturesSettings.focalPoint
    pinchToZoomDecelerationEnabled = gesturesSettings.pinchToZoomDecelerationEnabled
    rotateDecelerationEnabled = gesturesSettings.rotateDecelerationEnabled
    scrollDecelerationEnabled = gesturesSettings.scrollDecelerationEnabled
    increaseRotateThresholdWhenPinchingToZoom =
      gesturesSettings.increaseRotateThresholdWhenPinchingToZoom
    increasePinchToZoomThresholdWhenRotating =
      gesturesSettings.increasePinchToZoomThresholdWhenRotating
    zoomAnimationAmount = gesturesSettings.zoomAnimationAmount
    pinchScrollEnabled = gesturesSettings.pinchScrollEnabled
  }
}
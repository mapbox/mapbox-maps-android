package com.mapbox.maps.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.TransitionOptions
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

@Composable
fun MapboxMap(
  modifier: Modifier = Modifier,
  mapInitOptions: MapInitOptions? = null,
  content: @Composable MapboxMapScope.() -> Unit = {}, // null
) {
  if (LocalInspectionMode.current) {
    MapboxMapPlaceholder(modifier)
  } else {
    val mapView = MapView(
      LocalContext.current,
      mapInitOptions ?: MapInitOptions(LocalContext.current)
    )
    MapboxMapScopeInstance(mapView.getMapboxMap()).content()
    AndroidView(
      factory = { mapView },
      modifier = modifier
    )
  }
}

@JvmInline
value class Longitude(val longitude: Double)

@JvmInline
value class Latitude(val latitude: Double)

@LayoutScopeMarker
@Immutable
interface MapboxMapScope {

  @Stable
  fun setGestureInProgress(inProgress: Boolean)

  @Stable
  fun setCamera(cameraOptions: CameraOptions)

  @Stable
  fun setCenter(longitude: Longitude, latitude: Latitude, zoomLevel: Double? = null)

  @Stable
  fun loadStyleUri(
    styleUri: String, styleTransitionOptions: TransitionOptions? = null,
    onStyleLoaded: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener? = null,
  )

  @Stable
  fun addOnMapClickListener(onMapClickListener: OnMapClickListener)
}

internal class MapboxMapScopeInstance(private val mapboxMap: MapboxMap) : MapboxMapScope {

  @Stable
  override fun setGestureInProgress(inProgress: Boolean) {
    mapboxMap.setGestureInProgress(inProgress)
  }

  override fun setCamera(cameraOptions: CameraOptions) {
    mapboxMap.setCamera(cameraOptions)
  }

  override fun setCenter(longitude: Longitude, latitude: Latitude, zoomLevel: Double?) {
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(longitude.longitude, latitude.latitude))
        .zoom(zoomLevel)
        .build()
    )
  }

  override fun loadStyleUri(
    styleUri: String,
    styleTransitionOptions: TransitionOptions?,
    onStyleLoaded: Style.OnStyleLoaded?,
    onMapLoadErrorListener: OnMapLoadErrorListener?
  ) {
    mapboxMap.loadStyleUri(
      styleUri = styleUri,
      styleTransitionOptions = styleTransitionOptions,
      onStyleLoaded = onStyleLoaded,
      onMapLoadErrorListener = onMapLoadErrorListener,
    )
  }

  override fun addOnMapClickListener(onMapClickListener: OnMapClickListener) {
    mapboxMap.addOnMapClickListener(onMapClickListener)
  }
}

@Composable
private fun MapboxMapPlaceholder(
  modifier: Modifier,
  placeholderColor: Color = Color(0xFFD2D2D9),
  strokeWidthInDp: Int = 5,
  strokeColor: Color = Color.Blue,
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(placeholderColor)
      .drawBehind {
        drawRect(
          size = size, color = strokeColor,
          style = Stroke(width = strokeWidthInDp.dp.toPx())
        )
      },
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Mapbox Map",
      Modifier.wrapContentHeight()
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun MapboxMapPreview() {
  MapboxMap(
    Modifier
      .height(400.dp)
      .width(200.dp)
  )
}
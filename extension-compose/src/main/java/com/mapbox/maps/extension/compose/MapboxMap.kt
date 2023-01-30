package com.mapbox.maps.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap

/**
 * Entry point for adding a Mapbox Map instance to the Jetpack Compose UI.
 */
@Composable
@MapboxExperimental
public fun MapboxMap(
  /**
   * Modifier to be applied to the Mapbox map.
   */
  modifier: Modifier = Modifier,
  /**
   * Defines the initialisation configurations for a [MapboxMap].
   * It can only be set once and not mutable after the initialisation. Mutating the [MapInitOptions]
   * during recomposition will result in a [IllegalStateException].
   */
  mapInitOptions: MapInitOptions = MapInitOptions(LocalContext.current.applicationContext),
  /**
   * The content of the map.
   */
  content: @Composable () -> Unit
) {
  // display placeholder when in preview mode.
  if (LocalInspectionMode.current) {
    MapPreviewPlaceHolder(modifier)
    return
  }
  val context = LocalContext.current.applicationContext
  val mapView = remember {
    MapView(context, mapInitOptions)
  }
  AndroidView(
    factory = {
      mapView
    },
    modifier = modifier,
  )
}
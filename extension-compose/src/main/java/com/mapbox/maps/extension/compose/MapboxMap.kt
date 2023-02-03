package com.mapbox.maps.extension.compose

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapPreviewPlaceHolder
import com.mapbox.maps.extension.compose.internal.MapboxMapComposeNode
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import kotlinx.coroutines.awaitCancellation

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
   *
   * It can only be set once and not mutable after the initialisation. Mutating the [MapInitOptions]
   * during recomposition will result in a [IllegalStateException].
   */
  mapInitOptions: MapInitOptions = MapInitOptions(LocalContext.current.applicationContext),
  /**
   * Callback to be invoked when the user clicks on the map view.
   */
  onMapClickListener: OnMapClickListener? = null,
  /**
   * Callback to be invoked when the user long clicks on the map view.
   */
  onMapLongClickListener: OnMapLongClickListener? = null,
  /**
   * The content of the map.
   */
  content: (@Composable @MapboxMapComposable MapboxMapScope.() -> Unit)? = null
) {
  // display placeholder when in preview mode.
  if (LocalInspectionMode.current) {
    MapPreviewPlaceHolder(modifier, mapInitOptions)
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

  val parentComposition = rememberCompositionContext()
  val currentMapInitOptions by rememberUpdatedState(mapInitOptions)
  val currentOnMapClickListener by rememberUpdatedState(onMapClickListener)
  val currentOnMapLongClickListener by rememberUpdatedState(onMapLongClickListener)
  val currentContent by rememberUpdatedState(content)

  LaunchedEffect(Unit) {
    disposingComposition(
      Composition(
        MapApplier(mapView), parentComposition
      ).apply {
        setContent {
          MapboxMapComposeNode(
            currentMapInitOptions,
            currentOnMapClickListener,
            currentOnMapLongClickListener
          )
          currentContent?.let { MapboxMapScope.it() }
        }
      }
    )
  }
}

private suspend inline fun disposingComposition(composition: Composition) {
  try {
    awaitCancellation()
  } finally {
    composition.dispose()
  }
}
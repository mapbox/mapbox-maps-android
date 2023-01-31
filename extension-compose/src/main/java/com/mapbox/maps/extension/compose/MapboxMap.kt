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
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.internal.MapPreviewPlaceHolder
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures
import kotlinx.coroutines.awaitCancellation

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
private class MapboxMapNode(
  val controller: MapView,
  var clickListener: OnMapClickListener,
  var longClickListener: OnMapLongClickListener
) : MapNode {
  override fun onAttached() {
    controller.gestures.apply {
      addOnMapClickListener(clickListener)
      addOnMapLongClickListener(longClickListener)
    }
  }
}

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
  mapInitOptions: MapInitOptions = MapInitOptions(LocalContext.current),
  /**
   * Callback to be invoked when the user clicks on the map view.
   */
  onMapClickListener: OnMapClickListener = OnMapClickListener { false },
  /**
   * Callback to be invoked when the user long clicks on the map view.
   */
  onMapLongClickListener: OnMapLongClickListener = OnMapLongClickListener { false },
  /**
   * The content of the map.
   */
  content: (@Composable @MapboxMapComposable MapboxMapScope.() -> Unit)? = null
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

  val parentComposition = rememberCompositionContext()
  val currentContent by rememberUpdatedState(content)

  LaunchedEffect(Unit) {
    val composition = Composition(
      MapApplier(mapView), parentComposition
    ).apply {
      setContent {
        ComposeNode<MapboxMapNode, MapApplier>(
          factory = {
            MapboxMapNode(
              mapView,
              onMapClickListener,
              onMapLongClickListener
            )
          },
          update = {
            // input arguments updater
            update(onMapClickListener) {
              controller.gestures.apply {
                removeOnMapClickListener(this@update.clickListener)
                addOnMapClickListener(it)
              }
              this.clickListener = it
            }
            update(onMapLongClickListener) {
              controller.gestures.apply {
                removeOnMapLongClickListener(this@update.longClickListener)
                addOnMapLongClickListener(it)
              }
              this.longClickListener = it
            }
            update(mapInitOptions) {
              throw IllegalStateException(
                """
                Mutating MapInitOptions during composition is not allowed.
                """.trimIndent()
              )
            }
          }
        )
        currentContent?.let { MapboxMapScope.it() }
      }
    }
    try {
      awaitCancellation()
    } finally {
      composition.dispose()
    }
  }
}
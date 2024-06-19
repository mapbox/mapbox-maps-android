package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
@OptIn(MapboxExperimental::class)
private class MapboxMapNode(
  val controller: MapView,
  initialClickListener: OnMapClickListener?,
  initialLongClickListener: OnMapLongClickListener?,
  initialMapViewportState: MapViewportState,
) : MapNode() {
  var mapViewportState = initialMapViewportState
    set(value) {
      if (value == field) return
      field.setMap(null)
      field = value
      value.setMap(controller)
    }

  var clickListener: OnMapClickListener? = initialClickListener
    set(value) {
      controller.gestures.apply {
        field?.let {
          removeOnMapClickListener(it)
        }
        value?.let {
          addOnMapClickListener(it)
        }
      }
      field = value
    }
  var longClickListener: OnMapLongClickListener? = initialLongClickListener
    set(value) {
      controller.gestures.apply {
        field?.let {
          removeOnMapLongClickListener(it)
        }
        value?.let {
          addOnMapLongClickListener(it)
        }
      }
      field = value
    }

  override fun onAttached(parent: MapNode) {
    controller.gestures.apply {
      clickListener?.let {
        addOnMapClickListener(it)
      }
      longClickListener?.let {
        addOnMapLongClickListener(it)
      }
    }
    mapViewportState.setMap(controller)
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  override fun onClear() {
    cleanUp()
  }

  private fun cleanUp() {
    controller.gestures.apply {
      clickListener?.let {
        removeOnMapClickListener(it)
      }
      longClickListener?.let {
        removeOnMapLongClickListener(it)
      }
    }
    mapViewportState.setMap(null)
  }

  override fun toString(): String {
    return "MapboxMapNode()"
  }
}

@OptIn(MapboxExperimental::class)
@JvmSynthetic
@Composable
internal fun MapboxMapComposeNode(
  mapViewportState: MapViewportState,
  onMapClickListener: OnMapClickListener?,
  onMapLongClickListener: OnMapLongClickListener?,
) {
  val mapApplier = currentComposer.applier as MapApplier
  ComposeNode<MapboxMapNode, MapApplier>(
    factory = {
      MapboxMapNode(
        mapApplier.mapView,
        onMapClickListener,
        onMapLongClickListener,
        mapViewportState,
      )
    },
    update = {
      // input arguments updater
      update(mapViewportState) {
        this.mapViewportState = mapViewportState
      }
      update(onMapClickListener) { listener ->
        this.clickListener = listener
      }
      update(onMapLongClickListener) { listener ->
        this.longClickListener = listener
      }
    }
  )
}
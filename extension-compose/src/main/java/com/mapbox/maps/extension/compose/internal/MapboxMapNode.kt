package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
private class MapboxMapNode(
  val controller: MapView,
  initialClickListener: OnMapClickListener?,
  initialLongClickListener: OnMapLongClickListener?,
) : MapNode() {
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
  }

  override fun toString(): String {
    return "MapboxMapNode()"
  }
}

@JvmSynthetic
@Composable
internal fun MapboxMapComposeNode(
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
      )
    },
    update = {
      // input arguments updater
      update(onMapClickListener) { listener ->
        this.clickListener = listener
      }
      update(onMapLongClickListener) { listener ->
        this.longClickListener = listener
      }
    }
  )
}
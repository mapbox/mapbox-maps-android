package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.gestures

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
private class MapboxMapNode(
  val controller: MapView,
  var clickListener: OnMapClickListener?,
  var longClickListener: OnMapLongClickListener?
) : MapNode {
  override fun onAttached() {
    controller.gestures.apply {
      clickListener?.let {
        addOnMapClickListener(it)
      }
      longClickListener?.let {
        addOnMapLongClickListener(it)
      }
    }
  }

  override fun onRemoved() {
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
}

@JvmSynthetic
@Composable
internal fun MapboxMapComposeNode(
  mapInitOptions: MapInitOptions,
  onMapClickListener: OnMapClickListener?,
  onMapLongClickListener: OnMapLongClickListener?,
) {
  val mapApplier = currentComposer.applier as MapApplier
  ComposeNode<MapboxMapNode, MapApplier>(
    factory = {
      MapboxMapNode(
        mapApplier.mapView,
        onMapClickListener,
        onMapLongClickListener
      )
    },
    update = {
      // input arguments updater
      update(onMapClickListener) { listener ->
        val previousListener = this.clickListener
        controller.gestures.apply {
          previousListener?.let {
            removeOnMapClickListener(it)
          }
          listener?.let {
            addOnMapClickListener(it)
          }
        }
        this.clickListener = listener
      }
      update(onMapLongClickListener) { listener ->
        val previousListener = this.longClickListener
        controller.gestures.apply {
          previousListener?.let {
            removeOnMapLongClickListener(it)
          }
          listener?.let {
            addOnMapLongClickListener(it)
          }
        }
        this.longClickListener = listener
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
}
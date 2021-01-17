package com.mapbox.maps.plugin.location

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate

internal class LocationComponentPositionManager(
  private val style: StyleManagerInterface,
  private val styleState: MapStyleStateDelegate,
  private var layerAbove: String?,
  private var layerBelow: String?
) {
  /**
   * Returns true whenever layer above/below configuration has changed and requires re-layout.
   */
  fun update(layerAbove: String?, layerBelow: String?): Boolean {
    val requiresUpdate =
      (
        !(this.layerAbove == layerAbove || this.layerAbove != null && this.layerAbove == layerAbove) ||
          !(this.layerBelow == layerBelow || this.layerBelow != null && this.layerBelow == layerBelow)
        )
    this.layerAbove = layerAbove
    this.layerBelow = layerBelow
    return requiresUpdate
  }

  fun addLayerToMap(layer: LocationLayerWrapper) {
    when {
      layerAbove != null -> layer.bindTo(style, styleState, LayerPosition(layerAbove, null, null))
      layerBelow != null -> layer.bindTo(style, styleState, LayerPosition(null, layerBelow, null))
      else -> layer.bindTo(style, styleState, null)
    }
  }
}
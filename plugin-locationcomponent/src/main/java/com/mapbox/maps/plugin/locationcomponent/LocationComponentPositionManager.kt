package com.mapbox.maps.plugin.locationcomponent

import androidx.annotation.RestrictTo
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class LocationComponentPositionManager(
  private var style: MapboxStyleManager,
  internal var layerAbove: String?,
  internal var layerBelow: String?
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
      layerAbove != null -> layer.bindTo(style, LayerPosition(layerAbove, null, null))
      layerBelow != null -> layer.bindTo(style, LayerPosition(null, layerBelow, null))
      else -> layer.bindTo(style, null)
    }
  }

  fun updateStyle(style: MapboxStyleManager) {
    this.style = style
  }
}
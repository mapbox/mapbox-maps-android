package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.ViewPlugin
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettingsInterface

/**
 * Presenter interface for the ScaleBar.
 */
interface ScaleBarPlugin : ViewPlugin, MapSizePlugin, ScaleBarSettingsInterface {
  /**
   * How many meters in each pixel.
   */
  var distancePerPixel: Float
}
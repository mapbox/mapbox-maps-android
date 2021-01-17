package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings

/**
 * Interface for ScaleBar.
 *
 */
interface ScaleBar {
  /**
   * How many meters in each pixel.
   */
  var distancePerPixel: Float
  /**
   * Defines whether ScaleBar is enabled or disabled.
   */
  var enable: Boolean

  /**
   * Defines settings fo ScaleBar
   */
  var settings: ScaleBarSettings

  /**
   * Defines the width of mapView
   */
  var mapViewWidth: Float
}
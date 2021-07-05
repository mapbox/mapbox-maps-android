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

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval]
   * even if actual data did not change. If set to False scale bar will redraw only on demand.
   *
   * Defaults to False and should not be changed explicitly in most cases.
   * Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  var useContinuousRendering: Boolean
}
package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.MapSizePlugin
import com.mapbox.maps.plugin.ViewPlugin
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettingsInterface

/**
 * Presenter interface for the ScaleBar.
 */
interface ScaleBarPlugin : ViewPlugin, MapSizePlugin, ScaleBarSettingsInterface {
  /**
   * How many meters in each pixel.
   */
  var distancePerPixel: Float

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval]
   * even if actual data did not change. If set to False scale bar will redraw only on demand.
   *
   * Defaults to False and should not be changed explicitly in most cases.
   * Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  var useContinuousRendering: Boolean
}
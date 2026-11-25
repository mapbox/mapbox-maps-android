package com.mapbox.maps.extension.androidauto

import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental

/**
 * The kind of map that [MapboxCarMap] creates for each car surface.
 */
@MapboxExperimental
enum class MapboxCarMapType {
  /**
   * Renders a [MapSurface] directly onto the car surface. This is the default.
   */
  MAP_SURFACE,

  /**
   * Renders a [MapView] onto the car surface through a virtual display. Observers get the view
   * from [MapboxCarMapSurface.mapView]. Requires [com.mapbox.maps.MapInitOptions.textureView]
   * to be false and [com.mapbox.maps.MapInitOptions.plugins] to include
   * [com.mapbox.maps.plugin.Plugin.MAPBOX_LIFECYCLE_PLUGIN_ID].
   * Compass, logo, attribution, consolidated attribution, and scale bar plugins are excluded
   * from the supplied plugin list.
   * The car UI is responsible for providing the required logo and attribution.
   */
  MAP_VIEW,
}
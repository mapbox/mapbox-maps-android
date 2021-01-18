package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.geojson.Point
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings

internal interface LocationPuckManager {
  var settings: LocationComponentSettings

  fun initialize(style: StyleManagerInterface)

  fun isLayerInitialised(): Boolean

  fun updateSettings(settings: LocationComponentSettings)

  fun updateCurrentPosition(point: Point)

  fun updateCurrentBearing(bearing: Float)

  fun updateCurrentZoomLevel(zoomLevel: Double)
}
package com.mapbox.maps.plugin

import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.attribution.AttributionPlugin
import com.mapbox.maps.plugin.compass.CompassPlugin
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.logo.LogoPlugin
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin

sealed class Plugin(
  // unique id
  val pluginId: String,
  // instance is needed for custom plugins, for Mapbox ones null is passed
  val pluginInstance: MapPlugin?
) {
  class Camera(pluginId: String, pluginInstance: CameraAnimationsPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Gestures(pluginId: String, pluginInstance: GesturesPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Compass(pluginId: String, pluginInstance: CompassPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Logo(pluginId: String, pluginInstance: LogoPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Attribution(pluginId: String, pluginInstance: AttributionPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class LocationComponent(pluginId: String, pluginInstance: LocationComponentPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Annotation(pluginId: String, pluginInstance: AnnotationPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Scalebar(pluginId: String, pluginInstance: ScaleBarPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Lifecycle(pluginId: String, pluginInstance: ScaleBarPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class MapOverlay(pluginId: String, pluginInstance: MapOverlayPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  class Custom(pluginId: String, pluginInstance: MapPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  override fun toString(): String {
    return "pluginId = $pluginId, pluginInstance = ${pluginInstance?.javaClass}"
  }
}
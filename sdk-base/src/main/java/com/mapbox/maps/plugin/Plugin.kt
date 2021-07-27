package com.mapbox.maps.plugin

import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.attribution.AttributionPlugin
import com.mapbox.maps.plugin.compass.CompassPlugin
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePlugin
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.logo.LogoPlugin
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin

/**
 * Class describing plugin instance.
 *
 * For Mapbox plugins it is enough to provide plugin id from this class without plugin instance.
 * For user-defined plugins both unique id and plugin instance must be provided.
 */
sealed class Plugin(
  /**
   * Unique plugin id.
   */
  val id: String,
  /**
   * Plugin instance implementing [MapPlugin].
   * Must be non-null for user-defined plugins
   * or RuntimeException will occur when trying to initiate such plugin.
   */
  val instance: MapPlugin?
) {
  /**
   * Class for Camera plugin responsible for map camera.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [CameraAnimationsPlugin]
   */
  class Camera(pluginId: String, pluginInstance: CameraAnimationsPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Gestures plugin responsible for map gestures.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [GesturesPlugin]
   */
  class Gestures(pluginId: String, pluginInstance: GesturesPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Compass plugin responsible for compass widget.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [CompassPlugin]
   */
  class Compass(pluginId: String, pluginInstance: CompassPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Logo plugin responsible for logo widget.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [LogoPlugin]
   */
  class Logo(pluginId: String, pluginInstance: LogoPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Attribution plugin responsible for attribution widgets.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [AttributionPlugin]
   */
  class Attribution(pluginId: String, pluginInstance: AttributionPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Location component plugin responsible for basic location puck customization.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [LocationComponentPlugin]
   */
  class LocationComponent(pluginId: String, pluginInstance: LocationComponentPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Annotation plugin responsible for adding map annotations.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [AnnotationPlugin]
   */
  class Annotation(pluginId: String, pluginInstance: AnnotationPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Scalebar plugin responsible for scale bar widget.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [ScaleBarPlugin]
   */
  class Scalebar(pluginId: String, pluginInstance: ScaleBarPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Lifecycle plugin responsible for handling map view lifecycle events.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [MapboxLifecyclePlugin]
   */
  class Lifecycle(pluginId: String, pluginInstance: MapboxLifecyclePlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for Map Overlay plugin responsible for controlling map overlay.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [MapOverlayPlugin]
   */
  class MapOverlay(pluginId: String, pluginInstance: MapOverlayPlugin? = null) :
    Plugin(pluginId, pluginInstance)

  /**
   * Class for custom plugin.
   * May be used for any user-defined plugin that is supposed to be added to the map view.
   *
   * @param pluginId unique id.
   * @param pluginInstance instance of [MapPlugin]
   */
  class Custom(pluginId: String, pluginInstance: MapPlugin) :
    Plugin(pluginId, pluginInstance)

  override fun toString(): String {
    return "pluginId = $id, pluginInstance = ${instance?.javaClass}"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val that = other as Plugin
    if (that.id != id) {
      return false
    }
    return true
  }

  override fun hashCode(): Int {
    return 31 + id.hashCode()
  }

  companion object {
    /**
     * Id for Mapbox Camera Plugin.
     */
    const val MAPBOX_CAMERA_PLUGIN_ID = "MAPBOX_CAMERA_PLUGIN_ID"
    /**
     * Id for Mapbox Gestures Plugin.
     */
    const val MAPBOX_GESTURES_PLUGIN_ID = "MAPBOX_GESTURES_PLUGIN_ID"
    /**
     * Id for Mapbox Compass Plugin.
     */
    const val MAPBOX_COMPASS_PLUGIN_ID = "MAPBOX_COMPASS_PLUGIN_ID"
    /**
     * Id for Mapbox Logo Plugin.
     */
    const val MAPBOX_LOGO_PLUGIN_ID = "MAPBOX_LOGO_PLUGIN_ID"
    /**
     * Id for Mapbox Attribution Plugin.
     */
    const val MAPBOX_ATTRIBUTION_PLUGIN_ID = "MAPBOX_ATTRIBUTION_PLUGIN_ID"
    /**
     * Id for Mapbox Location Component Plugin.
     */
    const val MAPBOX_LOCATION_COMPONENT_PLUGIN_ID = "MAPBOX_LOCATION_COMPONENT_PLUGIN_ID"
    /**
     * Id for Mapbox Scalebar Plugin.
     */
    const val MAPBOX_SCALEBAR_PLUGIN_ID = "MAPBOX_SCALEBAR_PLUGIN_ID"
    /**
     * Id for Mapbox Annotation Plugin.
     */
    const val MAPBOX_ANNOTATION_PLUGIN_ID = "MAPBOX_ANNOTATION_PLUGIN_ID"
    /**
     * Id for Mapbox Lifecycle Plugin.
     */
    const val MAPBOX_LIFECYCLE_PLUGIN_ID = "MAPBOX_LIFECYCLE_PLUGIN_ID"
    /**
     * Id for Mapbox Map Overlay Plugin.
     */
    const val MAPBOX_MAP_OVERLAY_PLUGIN_ID = "MAPBOX_MAP_OVERLAY_PLUGIN_ID"
  }
}
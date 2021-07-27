package com.mapbox.maps.plugin

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
   * Must be non-null for user-defined plugins or RuntimeException will occur when trying to initiate such plugin.
   */
  val instance: MapPlugin?
) {
  /**
   * Should be used to create Mapbox official plugins.
   *
   * @param id unique id
   */
  class Mapbox(id: String) : Plugin(id, null)

  /**
   * Should be used to create custom user-defined plugins.
   *
   * @param id unique id
   * @param instance instance of user-defined [MapPlugin]
   */
  class Custom(id: String, instance: MapPlugin) : Plugin(id, instance)

  /**
   * toString implementation.
   */
  override fun toString(): String {
    return "pluginId = $id, pluginInstance = ${instance?.javaClass}"
  }

  /**
   * Equals implementation taking only id into account.
   */
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

  /**
   * Hash code implementation taking only id into account.
   */
  override fun hashCode(): Int {
    return 31 + id.hashCode()
  }

  /**
   * Static ids for Mapbox plugins.
   */
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
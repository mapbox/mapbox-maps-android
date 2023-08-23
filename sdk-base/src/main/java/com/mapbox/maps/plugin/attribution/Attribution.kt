package com.mapbox.maps.plugin.attribution

/**
 * Attribution model class.
 *
 * @property title the attribution title
 * @property titleAbbreviated the shortened attribution title
 * @property url the attribution URL
 */
data class Attribution(val title: String, val url: String) {

  val titleAbbreviated: String
    get() = if (title == OSM) OSM_ABBR else title

  /**
   * Attribution constant values
   *
   * @property OSM OpenStreetMap
   * @property OSM_ABBR OpenStreetMap abbreviated as OSM
   * @property TELEMETRY_SETTINGS Telemetry settings
   * @property PRIVACY_POLICY Privacy policy
   * @property ABOUT_MAPS_URL Mapbox about maps URL
   * @property ABOUT_TELEMETRY_URL Mapbox about telemetry URL
   * @property PRIVACY_POLICY_URL Mapbox privacy policy URL
   *
   */
  companion object {
    const val OSM = "OpenStreetMap"
    const val OSM_ABBR = "OSM"
    const val TELEMETRY_SETTINGS = "Telemetry Settings"
    const val PRIVACY_POLICY = "Mapbox Privacy Policy"
    const val ABOUT_MAPS_URL = "https://www.mapbox.com/about/maps/"
    const val ABOUT_TELEMETRY_URL = "https://www.mapbox.com/telemetry/"
    const val PRIVACY_POLICY_URL = "https://www.mapbox.com/legal/privacy#product-privacy-policy/"
  }
}
package com.mapbox.maps.plugin.attribution

/**
 * The attribution entries the sheet renders, grouped the way the sheet lays them out: the data
 * providers first, then "Improve this map", then the privacy policy alongside the consent
 * switches.
 */
internal data class AttributionSheetRows(
  /** Data provider links, in the order the style declared them. */
  val dataLinks: List<Attribution>,
  /** The "Improve this map" entry, whose URL is rebuilt against the live map when tapped. */
  val feedback: Attribution?,
  /** The privacy policy entry, shown with the consent switches rather than the data links. */
  val privacy: Attribution?,
) {
  internal companion object {
    // The consent rows the sheet renders itself, rather than as data links.
    private val CONSENT_URLS = setOf(
      Attribution.ABOUT_TELEMETRY_URL,
      Attribution.GEOFENCING_URL_MARKER,
    )

    private const val MAPBOX_PRIVACY_POLICY_URL = "https://www.mapbox.com/legal/privacy"

    fun from(attributions: List<Attribution>): AttributionSheetRows {
      val links = attributions.filterNot { it.isConsentRow() }
      val feedback = links.firstOrNull { it.isMapboxFeedback() }
      return AttributionSheetRows(
        dataLinks = links.filter { it !== feedback },
        feedback = feedback,
        privacy = attributions.firstOrNull { it.isMapboxPrivacyPolicy() },
      )
    }

    private fun Attribution.isConsentRow(): Boolean =
      url in CONSENT_URLS || isMapboxPrivacyPolicy()

    private fun Attribution.isMapboxPrivacyPolicy(): Boolean =
      url.startsWith(MAPBOX_PRIVACY_POLICY_URL)
  }
}
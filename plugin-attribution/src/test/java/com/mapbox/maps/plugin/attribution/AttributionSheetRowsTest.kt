package com.mapbox.maps.plugin.attribution

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AttributionSheetRowsTest {

  @Test
  fun dataLinksKeepStyleOrder() {
    val rows = AttributionSheetRows.from(
      listOf(
        attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
        attribution("© OpenStreetMap", "https://www.openstreetmap.org/about/"),
      )
    )

    assertEquals(listOf("© Mapbox", "© OpenStreetMap"), rows.dataLinks.map { it.title })
    assertNull(rows.feedback)
    assertNull(rows.privacy)
  }

  @Test
  fun feedbackIsSplitOutOfDataLinks() {
    val rows = AttributionSheetRows.from(
      listOf(
        attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
        attribution("Improve this map", "https://apps.mapbox.com/feedback/"),
      )
    )

    assertEquals(listOf("© Mapbox"), rows.dataLinks.map { it.title })
    assertEquals("Improve this map", rows.feedback?.title)
  }

  @Test
  fun consentAndPolicyEntriesAreNotDataLinks() {
    val rows = AttributionSheetRows.from(
      listOf(
        attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
        attribution("Telemetry", Attribution.ABOUT_TELEMETRY_URL),
        attribution("Geofencing", Attribution.GEOFENCING_URL_MARKER),
        attribution("Mapbox Privacy Policy", "https://www.mapbox.com/legal/privacy"),
      )
    )

    assertEquals(listOf("© Mapbox"), rows.dataLinks.map { it.title })
    assertEquals("Mapbox Privacy Policy", rows.privacy?.title)
  }

  @Test
  fun mapboxPrivacyPolicyIsRecognisedWithAndWithoutItsFragment() {
    listOf(Attribution.PRIVACY_POLICY_URL, "https://www.mapbox.com/legal/privacy").forEach { url ->
      val rows = AttributionSheetRows.from(
        listOf(
          attribution("© Mapbox", "https://www.mapbox.com/about/maps/"),
          attribution("Mapbox Privacy Policy", url),
        )
      )

      assertEquals(listOf("© Mapbox"), rows.dataLinks.map { it.title })
      assertEquals("Mapbox Privacy Policy", rows.privacy?.title)
    }
  }

  @Test
  fun providerUrlsCarryingConsentWordsStayDataLinks() {
    val rows = AttributionSheetRows.from(
      listOf(
        attribution("© Provider", "https://example.com/privacy-policy"),
        attribution("© Telemetry Co", "https://telemetry.example.com/about/"),
      )
    )

    assertEquals(
      listOf("© Provider", "© Telemetry Co"),
      rows.dataLinks.map { it.title }
    )
    assertNull(rows.privacy)
  }

  @Test
  fun emptyAttributionListYieldsNoRows() {
    val rows = AttributionSheetRows.from(emptyList())

    assertEquals(emptyList<Attribution>(), rows.dataLinks)
    assertNull(rows.feedback)
    assertNull(rows.privacy)
  }

  private fun attribution(title: String, url: String) = Attribution(title, url)
}
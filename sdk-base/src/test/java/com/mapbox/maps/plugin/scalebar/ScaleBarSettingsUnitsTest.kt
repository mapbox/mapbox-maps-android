package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.DistanceUnits
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for the interaction between isMetricUnits and distanceUnits properties in ScaleBarSettings.
 *
 * The Builder is a generated pure data holder — it does not sync isMetricUnits and distanceUnits.
 * Sync between the two fields is handled at the ScaleBarSettingsBase level (property setters).
 * These tests verify Builder defaults and independent field behavior.
 */
class ScaleBarSettingsUnitsTest {

  @Test
  fun testDefaultSettings() {
    val settings = ScaleBarSettings.Builder().build()
    assertEquals("Default distanceUnits should be METRIC", DistanceUnits.METRIC, settings.distanceUnits)
    assertTrue("Default isMetricUnits should be true", settings.isMetricUnits)
  }

  @Test
  fun testBuilderSetDistanceUnits() {
    val builder = ScaleBarSettings.Builder()

    builder.setDistanceUnits(DistanceUnits.IMPERIAL)
    val settings = builder.build()
    assertEquals("distanceUnits should be IMPERIAL", DistanceUnits.IMPERIAL, settings.distanceUnits)

    builder.setDistanceUnits(DistanceUnits.NAUTICAL)
    val nauticalSettings = builder.build()
    assertEquals("distanceUnits should be NAUTICAL", DistanceUnits.NAUTICAL, nauticalSettings.distanceUnits)
  }

  @Test
  fun testToBuilderPreservesState() {
    val original = ScaleBarSettings.Builder()
      .setDistanceUnits(DistanceUnits.NAUTICAL)
      .build()

    val rebuilt = original.toBuilder().build()
    assertEquals("toBuilder should preserve distanceUnits", DistanceUnits.NAUTICAL, rebuilt.distanceUnits)
    assertEquals("Settings should be equal", original, rebuilt)
  }

  @Test
  fun testChainedBuilderCalls() {
    val settings = ScaleBarSettings.Builder()
      .setDistanceUnits(DistanceUnits.IMPERIAL)
      .setDistanceUnits(DistanceUnits.NAUTICAL)
      .build()

    assertEquals("Final distanceUnits should be NAUTICAL", DistanceUnits.NAUTICAL, settings.distanceUnits)
  }

  @Test
  fun testDSLStyleBuilder() {
    val settings = ScaleBarSettings {
      distanceUnits = DistanceUnits.NAUTICAL
    }
    assertEquals("DSL builder should set NAUTICAL", DistanceUnits.NAUTICAL, settings.distanceUnits)
  }

  @Test
  fun testAllDistanceUnitsValues() {
    val builder = ScaleBarSettings.Builder()
    for (unit in DistanceUnits.values()) {
      builder.setDistanceUnits(unit)
      val settings = builder.build()
      assertEquals("distanceUnits should be set correctly for $unit", unit, settings.distanceUnits)
    }
  }
}
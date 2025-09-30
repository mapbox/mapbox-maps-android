package com.mapbox.maps.plugin.scalebar

import com.mapbox.maps.plugin.DistanceUnits
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for the interaction between isMetricUnits and units properties in ScaleBarSettings.
 * This ensures backward compatibility with the deprecated isMetricUnits property while
 * supporting the new units property.
 */
class ScaleBarSettingsUnitsTest {

  @Test
  fun testIsMetricUnitsToUnitsInteraction() {
    val builder = ScaleBarSettings.Builder()

    // Test setting isMetricUnits to true sets units to METRIC
    builder.setIsMetricUnits(true)
    val metricSettings = builder.build()
    assertEquals("Setting isMetricUnits to true should set units to METRIC", DistanceUnits.METRIC, metricSettings.distanceUnits)
    assertTrue("isMetricUnits should remain true", metricSettings.isMetricUnits)

    // Test setting isMetricUnits to false sets units to IMPERIAL
    builder.setIsMetricUnits(false)
    val imperialSettings = builder.build()
    assertEquals("Setting isMetricUnits to false should set units to IMPERIAL", DistanceUnits.IMPERIAL, imperialSettings.distanceUnits)
    assertFalse("isMetricUnits should remain false", imperialSettings.isMetricUnits)
  }

  @Test
  fun testUnitsToIsMetricUnitsInteraction() {
    val builder = ScaleBarSettings.Builder()

    // Test setting distanceUnits to METRIC sets isMetricUnits to true
    builder.setDistanceUnits(DistanceUnits.METRIC)
    val metricSettings = builder.build()
    assertTrue("Setting distanceUnits to METRIC should set isMetricUnits to true", metricSettings.isMetricUnits)
    assertEquals("distanceUnits should remain METRIC", DistanceUnits.METRIC, metricSettings.distanceUnits)

    // Test setting distanceUnits to IMPERIAL sets isMetricUnits to false
    builder.setDistanceUnits(DistanceUnits.IMPERIAL)
    val imperialSettings = builder.build()
    assertFalse("Setting distanceUnits to IMPERIAL should set isMetricUnits to false", imperialSettings.isMetricUnits)
    assertEquals("distanceUnits should remain IMPERIAL", DistanceUnits.IMPERIAL, imperialSettings.distanceUnits)

    // Test setting distanceUnits to NAUTICAL sets isMetricUnits to false
    builder.setDistanceUnits(DistanceUnits.NAUTICAL)
    val nauticalSettings = builder.build()
    assertFalse("Setting distanceUnits to NAUTICAL should set isMetricUnits to false", nauticalSettings.isMetricUnits)
    assertEquals("distanceUnits should remain NAUTICAL", DistanceUnits.NAUTICAL, nauticalSettings.distanceUnits)
  }

  @Test
  fun testScaleBarSettingsInitialization() {
    // Test default initialization
    val defaultBuilder = ScaleBarSettings.Builder()
    val defaultSettings = defaultBuilder.build()
    assertEquals("Default distanceUnits should be METRIC", DistanceUnits.METRIC, defaultSettings.distanceUnits)
    assertTrue("Default isMetricUnits should be true for metric distanceUnits", defaultSettings.isMetricUnits)

    // Test initialization with isMetricUnits false
    val imperialBuilder = ScaleBarSettings.Builder().setIsMetricUnits(false)
    val imperialSettings = imperialBuilder.build()
    assertEquals("isMetricUnits false should result in IMPERIAL distanceUnits", DistanceUnits.IMPERIAL, imperialSettings.distanceUnits)
    assertFalse("isMetricUnits should be false", imperialSettings.isMetricUnits)

    // Test initialization with explicit distanceUnits
    val nauticalBuilder = ScaleBarSettings.Builder().setDistanceUnits(DistanceUnits.NAUTICAL)
    val nauticalSettings = nauticalBuilder.build()
    assertEquals("Explicit distanceUnits should be preserved", DistanceUnits.NAUTICAL, nauticalSettings.distanceUnits)
    assertFalse("isMetricUnits should be false for nautical distanceUnits", nauticalSettings.isMetricUnits)
  }

  @Test
  fun testScaleBarUnitsBackwardCompatibility() {
    val builder = ScaleBarSettings.Builder()

    // Simulate legacy code setting isMetricUnits
    builder.setIsMetricUnits(true)
    var settings = builder.build()
    assertEquals("Legacy isMetricUnits=true should work with new distanceUnits property", DistanceUnits.METRIC, settings.distanceUnits)

    builder.setIsMetricUnits(false)
    settings = builder.build()
    assertEquals("Legacy isMetricUnits=false should work with new distanceUnits property", DistanceUnits.IMPERIAL, settings.distanceUnits)

    // Simulate new code setting distanceUnits while legacy property exists
    builder.setDistanceUnits(DistanceUnits.NAUTICAL)
    settings = builder.build()
    assertFalse("New distanceUnits=NAUTICAL should update legacy isMetricUnits property", settings.isMetricUnits)

    builder.setDistanceUnits(DistanceUnits.METRIC)
    settings = builder.build()
    assertTrue("New distanceUnits=METRIC should update legacy isMetricUnits property", settings.isMetricUnits)

    builder.setDistanceUnits(DistanceUnits.IMPERIAL)
    settings = builder.build()
    assertFalse("New distanceUnits=IMPERIAL should update legacy isMetricUnits property", settings.isMetricUnits)
  }

  @Test
  fun testChainedBuilderCalls() {
    // Test that chained builder calls work correctly
    val settings = ScaleBarSettings.Builder()
      .setIsMetricUnits(false)
      .setDistanceUnits(DistanceUnits.NAUTICAL)
      .build()

    assertEquals("Final distanceUnits should be NAUTICAL", DistanceUnits.NAUTICAL, settings.distanceUnits)
    assertFalse("Final isMetricUnits should be false for nautical distanceUnits", settings.isMetricUnits)
  }

  @Test
  fun testBuilderPropertyConsistency() {
    val builder = ScaleBarSettings.Builder()

    // Set distanceUnits first, then check builder properties
    builder.setDistanceUnits(DistanceUnits.IMPERIAL)
    assertEquals("Builder distanceUnits property should be IMPERIAL", DistanceUnits.IMPERIAL, builder.distanceUnits)
    assertFalse("Builder isMetricUnits property should be false", builder.isMetricUnits)

    // Set isMetricUnits, then check builder properties
    builder.setIsMetricUnits(true)
    assertEquals("Builder distanceUnits property should be METRIC", DistanceUnits.METRIC, builder.distanceUnits)
    assertTrue("Builder isMetricUnits property should be true", builder.isMetricUnits)

    // Set nautical distanceUnits, then check builder properties
    builder.setDistanceUnits(DistanceUnits.NAUTICAL)
    assertEquals("Builder distanceUnits property should be NAUTICAL", DistanceUnits.NAUTICAL, builder.distanceUnits)
    assertFalse("Builder isMetricUnits property should be false for nautical", builder.isMetricUnits)
  }

  @Test
  fun testDSLStyleBuilder() {
    // Test DSL-style builder with distanceUnits interaction
    val settings = ScaleBarSettings {
      isMetricUnits = false
      distanceUnits = DistanceUnits.NAUTICAL
    }

    assertEquals("DSL builder should result in NAUTICAL distanceUnits", DistanceUnits.NAUTICAL, settings.distanceUnits)
    assertFalse("DSL builder should result in isMetricUnits=false", settings.isMetricUnits)
  }

  @Test
  fun testToBuilderPreservesUnitsState() {
    // Create settings with nautical units
    val originalSettings = ScaleBarSettings.Builder()
      .setDistanceUnits(DistanceUnits.NAUTICAL)
      .build()

    // Convert back to builder and build again
    val newSettings = originalSettings.toBuilder().build()

    assertEquals("toBuilder should preserve units", DistanceUnits.NAUTICAL, newSettings.distanceUnits)
    assertFalse("toBuilder should preserve isMetricUnits", newSettings.isMetricUnits)
    assertEquals("Settings should be equal", originalSettings, newSettings)
  }

  @Test
  fun testAllUnitsValues() {
    val builder = ScaleBarSettings.Builder()

    // Test all DistanceUnits enum values
    for (unit in DistanceUnits.values()) {
      builder.setDistanceUnits(unit)
      val settings = builder.build()

      assertEquals("DistanceUnits should be set correctly", unit, settings.distanceUnits)

      val expectedIsMetric = unit == DistanceUnits.METRIC
      assertEquals("isMetricUnits should be correct for $unit", expectedIsMetric, settings.isMetricUnits)
    }
  }
}
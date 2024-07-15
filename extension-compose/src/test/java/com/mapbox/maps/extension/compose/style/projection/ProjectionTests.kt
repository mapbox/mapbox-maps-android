package com.mapbox.maps.extension.compose.style.projection

import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
public class ProjectionTests {
  @Test
  public fun `Projection name getter`() {
    assertEquals("globe", Projection.GLOBE.projectionNameOrNull)
    assertEquals("mercator", Projection.MERCATOR.projectionNameOrNull)
    assertNull(Projection.DEFAULT.projectionNameOrNull)
  }
}
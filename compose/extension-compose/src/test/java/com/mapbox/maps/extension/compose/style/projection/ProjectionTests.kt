package com.mapbox.maps.extension.compose.style.projection

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
public class ProjectionTests {
  @OptIn(MapboxExperimental::class)
  @Test
  public fun `Projection name getter`() {
    assertEquals("globe", Projection.GLOBE.projectionNameOrNull)
    assertEquals("mercator", Projection.MERCATOR.projectionNameOrNull)
    assertEquals("albers", Projection.ALBERS.projectionNameOrNull)
    assertNull(Projection.DEFAULT.projectionNameOrNull)
  }

  @OptIn(MapboxExperimental::class)
  @Test
  public fun `Projection center and parallels`() {
    val projection = Projection.albers(center = listOf(10.0, 50.0), parallels = listOf(43.0, 62.0))
    assertEquals("albers", projection.projectionNameOrNull)
    assertEquals(listOf(10.0, 50.0), projection.center)
    assertEquals(listOf(43.0, 62.0), projection.parallels)
    assertNull(Projection.ALBERS.center)
    assertNull(Projection.ALBERS.parallels)
  }
}
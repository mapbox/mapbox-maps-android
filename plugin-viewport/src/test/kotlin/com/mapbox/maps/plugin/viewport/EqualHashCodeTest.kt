package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.plugin.viewport.transition.ViewportTransitionOptions
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualHashCodeTest {
  @Test
  fun `ViewportCameraTransitionOptions hashCode and equals test`() {
    val clazz = ViewportTransitionOptions::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
package com.mapbox.maps.plugin.viewport

import com.mapbox.maps.plugin.viewport.transition.ViewportCameraTransitionOptions
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualHashCodeTest {
  @Test
  fun `ViewportCameraTransitionOptions hashCode and equals test`() {
    val clazz = ViewportCameraTransitionOptions::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
package com.mapbox.maps

import com.mapbox.maps.renderer.RendererError
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualsHashCodeTest {
  @Test
  fun `RendererError hashCode and equals test`() {
    val clazz = RendererError::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
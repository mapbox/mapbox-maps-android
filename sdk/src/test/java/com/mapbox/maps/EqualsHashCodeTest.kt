package com.mapbox.maps

import com.mapbox.maps.renderer.egl.AntialiasingConfig
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualsHashCodeTest {

  @Test
  fun `AntialiasingConfig hashCode and equals test`() {
    val clazz = AntialiasingConfig::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
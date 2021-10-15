package com.mapbox.maps

import com.mapbox.maps.renderer.egl.ConfigMSAA
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualsHashCodeTest {

  @Test
  fun `ConfigMSAA hashCode and equals test`() {
    val clazz = ConfigMSAA::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
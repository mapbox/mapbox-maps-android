package com.mapbox.maps.extension.style

import com.mapbox.maps.extension.style.types.StyleTransition
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualHashCodeTest {

  @Test
  fun `StyleTransition hashCode and equals test`() {
    val clazz = StyleTransition::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
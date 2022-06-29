package com.mapbox.maps

import com.mapbox.maps.renderer.RendererError
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
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

  @Test
  fun `ViewAnnotationUpdateMode hashCode and equals test`() {
    val clazz = ViewAnnotationUpdateMode::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
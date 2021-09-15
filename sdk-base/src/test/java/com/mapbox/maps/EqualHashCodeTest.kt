package com.mapbox.maps

import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualHashCodeTest {
  @Test
  fun `CameraAnimatorOptions hashCode and equals test`() {
    val clazz = CameraAnimatorOptions::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `MapAnimationOptions hashCode and equals test`() {
    val clazz = MapAnimationOptions::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}
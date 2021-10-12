package com.mapbox.maps

import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.viewport.experimental.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.experimental.data.FollowingViewportStateOptions
import com.mapbox.maps.plugin.viewport.experimental.data.OverviewViewportStateOptions
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualsHashCodeTest {
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

  @Test
  fun `Plugin hashCode and equals test`() {
    val clazz = Plugin::class.java
    EqualsVerifier.forClass(clazz)
      .withIgnoredFields("instance")
      .usingGetClass()
      .verify()
  }

  @Test
  fun `DefaultViewportTransitionOptions hashCode and equals test`() {
    EqualsVerifier.forClass(DefaultViewportTransitionOptions::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `FollowingViewportStateOptions hashCode and equals test`() {
    EqualsVerifier.forClass(FollowingViewportStateOptions::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `OverviewViewportStateOptions hashCode and equals test`() {
    EqualsVerifier.forClass(OverviewViewportStateOptions::class.java)
      .usingGetClass()
      .verify()
  }
}
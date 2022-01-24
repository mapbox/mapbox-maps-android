package com.mapbox.maps

import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.ViewportOptions
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
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
  fun `FollowPuckViewportStateOptions hashCode and equals test`() {
    EqualsVerifier.forClass(FollowPuckViewportStateOptions::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `FollowPuckViewportStateBearing hashCode and equals test`() {
    EqualsVerifier.forClass(FollowPuckViewportStateBearing::class.java)
      .usingGetClass()
      .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify()
  }

  @Test
  fun `ViewportStatus hashCode and equals test`() {
    EqualsVerifier.forClass(ViewportStatus::class.java)
      .usingGetClass()
      .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify()
  }

  @Test
  fun `OverviewViewportStateOptions hashCode and equals test`() {
    EqualsVerifier.forClass(OverviewViewportStateOptions::class.java)
      .usingGetClass()
      .verify()
  }
  @Test
  fun `ViewportPluginOptions hashCode and equals test`() {
    EqualsVerifier.forClass(ViewportOptions::class.java)
      .usingGetClass()
      .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify()
  }
}
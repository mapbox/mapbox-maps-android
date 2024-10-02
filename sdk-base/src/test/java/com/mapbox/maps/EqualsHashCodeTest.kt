package com.mapbox.maps

import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeatureStateKey
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.ClusterFeature
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
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
  @Test
  fun `ImageHolder hashCode and equals test`() {
    EqualsVerifier.forClass(ImageHolder::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `AttributionSettings hashCode and equals test`() {
    EqualsVerifier.forClass(AttributionSettings::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `CompassSettings hashCode and equals test`() {
    EqualsVerifier.forClass(CompassSettings::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `GesturesSettings hashCode and equals test`() {
    EqualsVerifier.forClass(GesturesSettings::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `LocationComponentSettings hashCode and equals test`() {
    EqualsVerifier.forClass(LocationComponentSettings::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `LogoSettings hashCode and equals test`() {
    EqualsVerifier.forClass(LogoSettings::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `ScaleBarSettings hashCode and equals test`() {
    EqualsVerifier.forClass(ScaleBarSettings::class.java)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `ClusterFeature hashCode and equals test`() {
    EqualsVerifier.forClass(ClusterFeature::class.java)
      .usingGetClass()
      .withPrefabValues(
        Feature::class.java,
        Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)),
        Feature.fromGeometry(Point.fromLngLat(1.0, 1.0)),
      )
      .verify()
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `FeatureState hashCode and equals test`() {
    EqualsVerifier.forClass(FeatureState::class.java)
      .usingGetClass()
      .verify()
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `TypedFeaturesetDescriptor hashCode and equals test`() {
    EqualsVerifier.forClass(TypedFeaturesetDescriptor::class.java)
      .usingGetClass()
      .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      .verify()
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `FeaturesetFeature hashCode and equals test`() {
    EqualsVerifier.forClass(FeaturesetFeature::class.java)
      .usingGetClass()
      .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
      // ignore public fields built from Feature
      .withIgnoredFields("geometry", "properties\$delegate")
      .withPrefabValues(
        Feature::class.java,
        Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)),
        Feature.fromGeometry(Point.fromLngLat(1.0, 1.0)),
      ).verify()
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun `FeatureStateKey hashCode and equals test`() {
    EqualsVerifier.forClass(FeatureStateKey::class.java)
      .usingGetClass()
      .verify()
  }
}
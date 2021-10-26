// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Basic smoke tests for CircleAnnotationManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CircleAnnotationManagerAndroidTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_attribution)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testCirclePitchAlignment() {
    val expectedValue = CirclePitchAlignment.MAP
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    circleAnnotationManager.circlePitchAlignment = expectedValue
    assertEquals(expectedValue, circleAnnotationManager.circlePitchAlignment)
    circleAnnotationManager.circlePitchAlignment = null
    val expectedDefaultValue = CirclePitchAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-alignment").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, circleAnnotationManager.circlePitchAlignment)
  }

  @Test
  fun testCirclePitchScale() {
    val expectedValue = CirclePitchScale.MAP
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    circleAnnotationManager.circlePitchScale = expectedValue
    assertEquals(expectedValue, circleAnnotationManager.circlePitchScale)
    circleAnnotationManager.circlePitchScale = null
    val expectedDefaultValue = CirclePitchScale.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-pitch-scale").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, circleAnnotationManager.circlePitchScale)
  }

  @Test
  fun testCircleTranslate() {
    val expectedValue = listOf(0.0, 1.0)
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    circleAnnotationManager.circleTranslate = expectedValue
    assertEquals(expectedValue, circleAnnotationManager.circleTranslate)
    circleAnnotationManager.circleTranslate = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate").silentUnwrap(), circleAnnotationManager.circleTranslate)
  }

  @Test
  fun testCircleTranslateAnchor() {
    val expectedValue = CircleTranslateAnchor.MAP
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    circleAnnotationManager.circleTranslateAnchor = expectedValue
    assertEquals(expectedValue, circleAnnotationManager.circleTranslateAnchor)
    circleAnnotationManager.circleTranslateAnchor = null
    val expectedDefaultValue = CircleTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("circle", "circle-translate-anchor").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, circleAnnotationManager.circleTranslateAnchor)
  }

  @Test
  fun create() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val annotation = circleAnnotationManager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleAnnotationManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val annotations = circleAnnotationManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), circleAnnotationManager.annotations[0])
    val annotations1 = circleAnnotationManager.create(featureCollection)
    assertEquals(annotations1.first(), circleAnnotationManager.annotations[1])
  }

  @Test
  fun createList() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleAnnotationManager.create(list)
    assertEquals(annotations[0], circleAnnotationManager.annotations[0])
    assertEquals(annotations[1], circleAnnotationManager.annotations[1])
  }

  @Test
  fun update() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val annotation = circleAnnotationManager.create(CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, circleAnnotationManager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    circleAnnotationManager.update(annotation)
    assertEquals(annotation, circleAnnotationManager.annotations[0])
  }

  @Test
  fun updateList() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleAnnotationManager.create(list)
    assertEquals(annotations[0], circleAnnotationManager.annotations[0])
    assertEquals(annotations[1], circleAnnotationManager.annotations[1])
    annotations[0].point = Point.fromLngLat(1.0, 1.0)
    annotations[1].point = Point.fromLngLat(1.0, 1.0)
    circleAnnotationManager.update(annotations)
    assertEquals(annotations[0], circleAnnotationManager.annotations[0])
    assertEquals(annotations[1], circleAnnotationManager.annotations[1])
  }

  @Test
  fun delete() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val annotation = circleAnnotationManager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleAnnotationManager.annotations[0])
    circleAnnotationManager.delete(annotation)
    assertTrue(circleAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleAnnotationManager.create(list)
    assertEquals(annotations[0], circleAnnotationManager.annotations[0])
    assertEquals(annotations[1], circleAnnotationManager.annotations[1])

    circleAnnotationManager.delete(annotations)
    assertTrue(circleAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleAnnotationManager.create(list)
    assertEquals(annotations[0], circleAnnotationManager.annotations[0])
    assertEquals(annotations[1], circleAnnotationManager.annotations[1])

    circleAnnotationManager.deleteAll()
    assertTrue(circleAnnotationManager.annotations.isEmpty())
  }
}
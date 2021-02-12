// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.generated.CircleOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for CircleManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CircleManagerAndroidTest : BaseMapTest() {
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
    val testValue = CirclePitchAlignment.MAP
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    circleManager.circlePitchAlignment = testValue
    assertEquals(testValue, circleManager.circlePitchAlignment)
  }

  @Test
  fun testCirclePitchScale() {
    val testValue = CirclePitchScale.MAP
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    circleManager.circlePitchScale = testValue
    assertEquals(testValue, circleManager.circlePitchScale)
  }

  @Test
  fun testCircleTranslate() {
    val testValue = listOf(0.0, 1.0)
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    circleManager.circleTranslate = testValue
    assertEquals(testValue, circleManager.circleTranslate)
  }

  @Test
  fun testCircleTranslateAnchor() {
    val testValue = CircleTranslateAnchor.MAP
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    circleManager.circleTranslateAnchor = testValue
    assertEquals(testValue, circleManager.circleTranslateAnchor)
  }

  @Test
  fun create() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val annotation = circleManager.create(
      CircleOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val annotations = circleManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), circleManager.annotations[0])
    val annotations1 = circleManager.create(featureCollection)
    assertEquals(annotations1.first(), circleManager.annotations[1])
  }

  @Test
  fun createList() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val list = listOf(
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleManager.create(list)
    assertEquals(annotations[0], circleManager.annotations[0])
    assertEquals(annotations[1], circleManager.annotations[1])
  }

  @Test
  fun update() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val annotation = circleManager.create(CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, circleManager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    circleManager.update(annotation)
    assertEquals(annotation, circleManager.annotations[0])
  }

  @Test
  fun updateList() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val list = listOf(
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleManager.create(list)
    assertEquals(annotations[0], circleManager.annotations[0])
    assertEquals(annotations[1], circleManager.annotations[1])
    annotations[0].point = Point.fromLngLat(1.0, 1.0)
    annotations[1].point = Point.fromLngLat(1.0, 1.0)
    circleManager.update(annotations)
    assertEquals(annotations[0], circleManager.annotations[0])
    assertEquals(annotations[1], circleManager.annotations[1])
  }

  @Test
  fun delete() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val annotation = circleManager.create(
      CircleOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleManager.annotations[0])
    circleManager.delete(annotation)
    assertTrue(circleManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val list = listOf(
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleManager.create(list)
    assertEquals(annotations[0], circleManager.annotations[0])
    assertEquals(annotations[1], circleManager.annotations[1])

    circleManager.delete(annotations)
    assertTrue(circleManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val circleManager = mapView.getAnnotationPlugin().createCircleManager(mapView)
    val list = listOf(
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = circleManager.create(list)
    assertEquals(annotations[0], circleManager.annotations[0])
    assertEquals(annotations[1], circleManager.annotations[1])

    circleManager.deleteAll()
    assertTrue(circleManager.annotations.isEmpty())
  }
}
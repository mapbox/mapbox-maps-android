// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

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
    val testValue = CirclePitchAlignment.MAP
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    circleAnnotationManager.circlePitchAlignment = testValue
    assertEquals(testValue, circleAnnotationManager.circlePitchAlignment)
  }

  @Test
  fun testCirclePitchScale() {
    val testValue = CirclePitchScale.MAP
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    circleAnnotationManager.circlePitchScale = testValue
    assertEquals(testValue, circleAnnotationManager.circlePitchScale)
  }

  @Test
  fun testCircleTranslate() {
    val testValue = listOf(0.0, 1.0)
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    circleAnnotationManager.circleTranslate = testValue
    assertEquals(testValue, circleAnnotationManager.circleTranslate)
  }

  @Test
  fun testCircleTranslateAnchor() {
    val testValue = CircleTranslateAnchor.MAP
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    circleAnnotationManager.circleTranslateAnchor = testValue
    assertEquals(testValue, circleAnnotationManager.circleTranslateAnchor)
  }

  @Test
  fun create() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    val annotation = circleAnnotationManager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleAnnotationManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val annotations = circleAnnotationManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), circleAnnotationManager.annotations[0])
    val annotations1 = circleAnnotationManager.create(featureCollection)
    assertEquals(annotations1.first(), circleAnnotationManager.annotations[1])
  }

  @Test
  fun createList() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
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
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    val annotation = circleAnnotationManager.create(CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, circleAnnotationManager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    circleAnnotationManager.update(annotation)
    assertEquals(annotation, circleAnnotationManager.annotations[0])
  }

  @Test
  fun updateList() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
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
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    val annotation = circleAnnotationManager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleAnnotationManager.annotations[0])
    circleAnnotationManager.delete(annotation)
    assertTrue(circleAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteAndAdd() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
    val annotation = circleAnnotationManager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, circleAnnotationManager.annotations[0])
    circleAnnotationManager.delete(annotation)
    assertTrue(circleAnnotationManager.annotations.isEmpty())
    circleAnnotationManager.addAnnotation(annotation)
    assertEquals(annotation, circleAnnotationManager.annotations[0])
  }

  @Test
  fun deleteList() {
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
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
    val circleAnnotationManager = mapView.annotations.createCircleAnnotationManager(mapView)
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
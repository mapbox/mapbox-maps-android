// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.R
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.generated.FillOptions
import com.mapbox.maps.plugin.annotation.generated.createFillManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for FillManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class FillManagerAndroidTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_attribution)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testFillAntialias() {
    val testValue = true
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    fillManager.fillAntialias = testValue
    assertEquals(testValue, fillManager.fillAntialias)
  }

  @Test
  fun testFillTranslate() {
    val testValue = listOf(0.0, 1.0)
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    fillManager.fillTranslate = testValue
    assertEquals(testValue, fillManager.fillTranslate)
  }

  @Test
  fun testFillTranslateAnchor() {
    val testValue = FillTranslateAnchor.MAP
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    fillManager.fillTranslateAnchor = testValue
    assertEquals(testValue, fillManager.fillTranslateAnchor)
  }

  @Test
  fun create() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val annotation = fillManager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))
    )
    assertEquals(annotation, fillManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Polygon.fromLngLats(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))))
    val annotations = fillManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), fillManager.annotations[0])
    val annotations1 = fillManager.create(featureCollection)
    assertEquals(annotations1.first(), fillManager.annotations[1])
  }

  @Test
  fun createList() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = fillManager.create(list)
    assertEquals(annotations[0], fillManager.annotations[0])
    assertEquals(annotations[1], fillManager.annotations[1])
  }

  @Test
  fun update() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val annotation = fillManager.create(FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0)))))
    assertEquals(annotation, fillManager.annotations[0])
    annotation.points = listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0)))
    fillManager.update(annotation)
    assertEquals(annotation, fillManager.annotations[0])
  }

  @Test
  fun updateList() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = fillManager.create(list)
    assertEquals(annotations[0], fillManager.annotations[0])
    assertEquals(annotations[1], fillManager.annotations[1])
    annotations[0].points = listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))
    annotations[1].points = listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    fillManager.update(annotations)
    assertEquals(annotations[0], fillManager.annotations[0])
    assertEquals(annotations[1], fillManager.annotations[1])
  }

  @Test
  fun delete() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val annotation = fillManager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))
    )
    assertEquals(annotation, fillManager.annotations[0])
    fillManager.delete(annotation)
    assertTrue(fillManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 0.0))))
    )
    val annotations = fillManager.create(list)
    assertEquals(annotations[0], fillManager.annotations[0])
    assertEquals(annotations[1], fillManager.annotations[1])

    fillManager.delete(annotations)
    assertTrue(fillManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val fillManager = mapView.getAnnotationPlugin().createFillManager(mapView)
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 0.0))))
    )
    val annotations = fillManager.create(list)
    assertEquals(annotations[0], fillManager.annotations[0])
    assertEquals(annotations[1], fillManager.annotations[1])

    fillManager.deleteAll()
    assertTrue(fillManager.annotations.isEmpty())
  }
}
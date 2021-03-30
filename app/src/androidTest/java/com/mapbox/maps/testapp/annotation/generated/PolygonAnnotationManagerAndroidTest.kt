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
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for PolygonAnnotationManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class PolygonAnnotationManagerAndroidTest : BaseMapTest() {
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
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    polygonAnnotationManager.fillAntialias = testValue
    assertEquals(testValue, polygonAnnotationManager.fillAntialias)
  }

  @Test
  fun testFillTranslate() {
    val testValue = listOf(0.0, 1.0)
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    polygonAnnotationManager.fillTranslate = testValue
    assertEquals(testValue, polygonAnnotationManager.fillTranslate)
  }

  @Test
  fun testFillTranslateAnchor() {
    val testValue = FillTranslateAnchor.MAP
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    polygonAnnotationManager.fillTranslateAnchor = testValue
    assertEquals(testValue, polygonAnnotationManager.fillTranslateAnchor)
  }

  @Test
  fun create() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val annotation = polygonAnnotationManager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))
    )
    assertEquals(annotation, polygonAnnotationManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Polygon.fromLngLats(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))))
    val annotations = polygonAnnotationManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), polygonAnnotationManager.annotations[0])
    val annotations1 = polygonAnnotationManager.create(featureCollection)
    assertEquals(annotations1.first(), polygonAnnotationManager.annotations[1])
  }

  @Test
  fun createList() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val list = listOf(
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = polygonAnnotationManager.create(list)
    assertEquals(annotations[0], polygonAnnotationManager.annotations[0])
    assertEquals(annotations[1], polygonAnnotationManager.annotations[1])
  }

  @Test
  fun update() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val annotation = polygonAnnotationManager.create(PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0)))))
    assertEquals(annotation, polygonAnnotationManager.annotations[0])
    annotation.points = listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0)))
    polygonAnnotationManager.update(annotation)
    assertEquals(annotation, polygonAnnotationManager.annotations[0])
  }

  @Test
  fun updateList() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val list = listOf(
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = polygonAnnotationManager.create(list)
    assertEquals(annotations[0], polygonAnnotationManager.annotations[0])
    assertEquals(annotations[1], polygonAnnotationManager.annotations[1])
    annotations[0].points = listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))
    annotations[1].points = listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    polygonAnnotationManager.update(annotations)
    assertEquals(annotations[0], polygonAnnotationManager.annotations[0])
    assertEquals(annotations[1], polygonAnnotationManager.annotations[1])
  }

  @Test
  fun delete() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val annotation = polygonAnnotationManager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))
    )
    assertEquals(annotation, polygonAnnotationManager.annotations[0])
    polygonAnnotationManager.delete(annotation)
    assertTrue(polygonAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val list = listOf(
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 0.0))))
    )
    val annotations = polygonAnnotationManager.create(list)
    assertEquals(annotations[0], polygonAnnotationManager.annotations[0])
    assertEquals(annotations[1], polygonAnnotationManager.annotations[1])

    polygonAnnotationManager.delete(annotations)
    assertTrue(polygonAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val polygonAnnotationManager = mapView.getAnnotationPlugin().createPolygonAnnotationManager(mapView)
    val list = listOf(
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 0.0))))
    )
    val annotations = polygonAnnotationManager.create(list)
    assertEquals(annotations[0], polygonAnnotationManager.annotations[0])
    assertEquals(annotations[1], polygonAnnotationManager.annotations[1])

    polygonAnnotationManager.deleteAll()
    assertTrue(polygonAnnotationManager.annotations.isEmpty())
  }
}
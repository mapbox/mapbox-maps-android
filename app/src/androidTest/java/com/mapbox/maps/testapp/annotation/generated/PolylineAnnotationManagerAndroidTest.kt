// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for PolylineAnnotationManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class PolylineAnnotationManagerAndroidTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_attribution)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testLineCap() {
    val testValue = LineCap.BUTT
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    polylineAnnotationManager.lineCap = testValue
    assertEquals(testValue, polylineAnnotationManager.lineCap)
  }

  @Test
  fun testLineMiterLimit() {
    val testValue = 1.0
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    polylineAnnotationManager.lineMiterLimit = testValue
    assertEquals(testValue, polylineAnnotationManager.lineMiterLimit)
  }

  @Test
  fun testLineRoundLimit() {
    val testValue = 1.0
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    polylineAnnotationManager.lineRoundLimit = testValue
    assertEquals(testValue, polylineAnnotationManager.lineRoundLimit)
  }

  @Test
  fun testLineDasharray() {
    val testValue = listOf(1.0, 2.0)
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    polylineAnnotationManager.lineDasharray = testValue
    assertEquals(testValue, polylineAnnotationManager.lineDasharray)
  }

  @Test
  fun testLineTranslate() {
    val testValue = listOf(0.0, 1.0)
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    polylineAnnotationManager.lineTranslate = testValue
    assertEquals(testValue, polylineAnnotationManager.lineTranslate)
  }

  @Test
  fun testLineTranslateAnchor() {
    val testValue = LineTranslateAnchor.MAP
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    polylineAnnotationManager.lineTranslateAnchor = testValue
    assertEquals(testValue, polylineAnnotationManager.lineTranslateAnchor)
  }

  @Test
  fun create() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val annotation = polylineAnnotationManager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, polylineAnnotationManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))))
    val annotations = polylineAnnotationManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), polylineAnnotationManager.annotations[0])
    val annotations1 = polylineAnnotationManager.create(featureCollection)
    assertEquals(annotations1.first(), polylineAnnotationManager.annotations[1])
  }

  @Test
  fun createList() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = polylineAnnotationManager.create(list)
    assertEquals(annotations[0], polylineAnnotationManager.annotations[0])
    assertEquals(annotations[1], polylineAnnotationManager.annotations[1])
  }

  @Test
  fun update() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val annotation = polylineAnnotationManager.create(PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))))
    assertEquals(annotation, polylineAnnotationManager.annotations[0])
    annotation.points = listOf(Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 1.0))
    annotation.lineJoin = LineJoin.BEVEL
    polylineAnnotationManager.update(annotation)
    assertEquals(annotation, polylineAnnotationManager.annotations[0])
    assertEquals(LineJoin.BEVEL, annotation.lineJoin)
  }

  @Test
  fun updateList() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = polylineAnnotationManager.create(list)
    assertEquals(annotations[0], polylineAnnotationManager.annotations[0])
    assertEquals(annotations[1], polylineAnnotationManager.annotations[1])
    annotations[0].points = listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 0.0))
    annotations[1].points = listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 0.0))
    polylineAnnotationManager.update(annotations)
    assertEquals(annotations[0], polylineAnnotationManager.annotations[0])
    assertEquals(annotations[1], polylineAnnotationManager.annotations[1])
  }

  @Test
  fun delete() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val annotation = polylineAnnotationManager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, polylineAnnotationManager.annotations[0])
    polylineAnnotationManager.delete(annotation)
    assertTrue(polylineAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteAndAdd() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val annotation = polylineAnnotationManager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, polylineAnnotationManager.annotations[0])
    polylineAnnotationManager.delete(annotation)
    assertTrue(polylineAnnotationManager.annotations.isEmpty())
    polylineAnnotationManager.addAnnotation(annotation)
    assertEquals(annotation, polylineAnnotationManager.annotations[0])
  }

  @Test
  fun deleteList() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = polylineAnnotationManager.create(list)
    assertEquals(annotations[0], polylineAnnotationManager.annotations[0])
    assertEquals(annotations[1], polylineAnnotationManager.annotations[1])

    polylineAnnotationManager.delete(annotations)
    assertTrue(polylineAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager(mapView)
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = polylineAnnotationManager.create(list)
    assertEquals(annotations[0], polylineAnnotationManager.annotations[0])
    assertEquals(annotations[1], polylineAnnotationManager.annotations[1])

    polylineAnnotationManager.deleteAll()
    assertTrue(polylineAnnotationManager.annotations.isEmpty())
  }
}
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
import com.mapbox.maps.plugin.annotation.generated.LineOptions
import com.mapbox.maps.plugin.annotation.generated.getLineManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for LineManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class LineManagerAndroidTest : BaseMapTest() {
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
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    lineManager.lineCap = testValue
    assertEquals(testValue, lineManager.lineCap)
  }

  @Test
  fun testLineMiterLimit() {
    val testValue = 1.0
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    lineManager.lineMiterLimit = testValue
    assertEquals(testValue, lineManager.lineMiterLimit)
  }

  @Test
  fun testLineRoundLimit() {
    val testValue = 1.0
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    lineManager.lineRoundLimit = testValue
    assertEquals(testValue, lineManager.lineRoundLimit)
  }

  @Test
  fun testLineTranslate() {
    val testValue = listOf(0.0, 1.0)
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    lineManager.lineTranslate = testValue
    assertEquals(testValue, lineManager.lineTranslate)
  }

  @Test
  fun testLineTranslateAnchor() {
    val testValue = LineTranslateAnchor.MAP
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    lineManager.lineTranslateAnchor = testValue
    assertEquals(testValue, lineManager.lineTranslateAnchor)
  }

  @Test
  fun testLineDasharray() {
    val testValue = listOf(1.0, 2.0)
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    lineManager.lineDasharray = testValue
    assertEquals(testValue, lineManager.lineDasharray)
  }

  @Test
  fun create() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val annotation = lineManager.create(
      LineOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, lineManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(listOf(Point.fromLngLat(0.0, 0.0)))))
    val annotations = lineManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), lineManager.annotations[0])
    val annotations1 = lineManager.create(featureCollection)
    assertEquals(annotations1.first(), lineManager.annotations[1])
  }

  @Test
  fun createList() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val list = listOf(
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = lineManager.create(list)
    assertEquals(annotations[0], lineManager.annotations[0])
    assertEquals(annotations[1], lineManager.annotations[1])
  }

  @Test
  fun update() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val annotation = lineManager.create(LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))))
    assertEquals(annotation, lineManager.annotations[0])
    annotation.points = listOf(Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 1.0))
    lineManager.update(annotation)
    assertEquals(annotation, lineManager.annotations[0])
  }

  @Test
  fun updateList() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val list = listOf(
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = lineManager.create(list)
    assertEquals(annotations[0], lineManager.annotations[0])
    assertEquals(annotations[1], lineManager.annotations[1])
    annotations[0].points = listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 0.0))
    annotations[1].points = listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 0.0))
    lineManager.update(annotations)
    assertEquals(annotations[0], lineManager.annotations[0])
    assertEquals(annotations[1], lineManager.annotations[1])
  }

  @Test
  fun delete() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val annotation = lineManager.create(
      LineOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, lineManager.annotations[0])
    lineManager.delete(annotation)
    assertTrue(lineManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val list = listOf(
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = lineManager.create(list)
    assertEquals(annotations[0], lineManager.annotations[0])
    assertEquals(annotations[1], lineManager.annotations[1])

    lineManager.delete(annotations)
    assertTrue(lineManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val lineManager = mapView.getAnnotationPlugin().getLineManager()
    val list = listOf(
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      LineOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = lineManager.create(list)
    assertEquals(annotations[0], lineManager.annotations[0])
    assertEquals(annotations[1], lineManager.annotations[1])

    lineManager.deleteAll()
    assertTrue(lineManager.annotations.isEmpty())
  }
}
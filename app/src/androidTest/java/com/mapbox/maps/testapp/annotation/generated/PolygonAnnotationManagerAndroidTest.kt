// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.R
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

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
    rule.runOnUiThread {
      val expectedValue = true
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillAntialias = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillAntialias)
      polygonAnnotationManager.fillAntialias = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-antialias").silentUnwrap(), polygonAnnotationManager.fillAntialias)
    }
  }

  @Test
  fun testFillTranslate() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillTranslate = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillTranslate)
      polygonAnnotationManager.fillTranslate = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate").silentUnwrap(), polygonAnnotationManager.fillTranslate)
    }
  }

  @Test
  fun testFillTranslateAnchor() {
    rule.runOnUiThread {
      val expectedValue = FillTranslateAnchor.MAP
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillTranslateAnchor = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillTranslateAnchor)
      polygonAnnotationManager.fillTranslateAnchor = null
      val expectedDefaultValue = FillTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, polygonAnnotationManager.fillTranslateAnchor)
    }
  }

  @Test
  fun create() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      val annotation = polygonAnnotationManager.create(
        PolygonAnnotationOptions()
          .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))
      )
      assertEquals(annotation, polygonAnnotationManager.annotations[0])
    }
  }

  @Test
  fun createFromFeature() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      val featureCollection =
        FeatureCollection.fromFeature(Feature.fromGeometry(Polygon.fromLngLats(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))))
      val annotations = polygonAnnotationManager.create(featureCollection.toJson())
      assertEquals(annotations.first(), polygonAnnotationManager.annotations[0])
      val annotations1 = polygonAnnotationManager.create(featureCollection)
      assertEquals(annotations1.first(), polygonAnnotationManager.annotations[1])
    }
  }

  @Test
  fun createList() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      val list = listOf(
        PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0)))),
        PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      )
      val annotations = polygonAnnotationManager.create(list)
      assertEquals(annotations[0], polygonAnnotationManager.annotations[0])
      assertEquals(annotations[1], polygonAnnotationManager.annotations[1])
    }
  }

  @Test
  fun update() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      val annotation = polygonAnnotationManager.create(PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0)))))
      assertEquals(annotation, polygonAnnotationManager.annotations[0])
      annotation.points = listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0)))
      polygonAnnotationManager.update(annotation)
      assertEquals(annotation, polygonAnnotationManager.annotations[0])
    }
  }

  @Test
  fun updateList() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
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
  }

  @Test
  fun delete() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      val annotation = polygonAnnotationManager.create(
        PolygonAnnotationOptions()
          .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0), Point.fromLngLat(0.0, 1.0), Point.fromLngLat(1.0, 0.0))))
      )
      assertEquals(annotation, polygonAnnotationManager.annotations[0])
      polygonAnnotationManager.delete(annotation)
      assertTrue(polygonAnnotationManager.annotations.isEmpty())
    }
  }

  @Test
  fun deleteList() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
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
  }

  @Test
  fun deleteAll() {
    rule.runOnUiThread {
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
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

  fun ActivityScenarioRule<*>.runOnUiThread(block: () -> Unit) {
    this.scenario.onActivity {
      it.runOnUiThread(block)
    }
  }
}
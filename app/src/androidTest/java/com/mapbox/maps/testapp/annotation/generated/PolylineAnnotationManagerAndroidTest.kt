// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

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
    rule.runOnUiThread {
      val expectedValue = LineCap.BUTT
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineCap = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineCap)
      polylineAnnotationManager.lineCap = null
      val expectedDefaultValue = LineCap.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-cap").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, polylineAnnotationManager.lineCap)
    }
  }

  @Test
  fun testLineMiterLimit() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineMiterLimit = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineMiterLimit)
      polylineAnnotationManager.lineMiterLimit = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-miter-limit").silentUnwrap(), polylineAnnotationManager.lineMiterLimit)
    }
  }

  @Test
  fun testLineRoundLimit() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineRoundLimit = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineRoundLimit)
      polylineAnnotationManager.lineRoundLimit = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-round-limit").silentUnwrap(), polylineAnnotationManager.lineRoundLimit)
    }
  }

  @Test
  fun testLineDasharray() {
    rule.runOnUiThread {
      val expectedValue = listOf(1.0, 2.0)
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineDasharray = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineDasharray)
      polylineAnnotationManager.lineDasharray = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-dasharray").silentUnwrap(), polylineAnnotationManager.lineDasharray)
    }
  }

  @Test
  fun testLineDepthOcclusionFactor() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineDepthOcclusionFactor = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineDepthOcclusionFactor)
      polylineAnnotationManager.lineDepthOcclusionFactor = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-depth-occlusion-factor").silentUnwrap(), polylineAnnotationManager.lineDepthOcclusionFactor)
    }
  }

  @Test
  fun testLineEmissiveStrength() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineEmissiveStrength = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineEmissiveStrength)
      polylineAnnotationManager.lineEmissiveStrength = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-emissive-strength").silentUnwrap(), polylineAnnotationManager.lineEmissiveStrength)
    }
  }

  @Test
  fun testLineTranslate() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineTranslate = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineTranslate)
      polylineAnnotationManager.lineTranslate = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate").silentUnwrap(), polylineAnnotationManager.lineTranslate)
    }
  }

  @Test
  fun testLineTranslateAnchor() {
    rule.runOnUiThread {
      val expectedValue = LineTranslateAnchor.MAP
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineTranslateAnchor = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineTranslateAnchor)
      polylineAnnotationManager.lineTranslateAnchor = null
      val expectedDefaultValue = LineTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-translate-anchor").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, polylineAnnotationManager.lineTranslateAnchor)
    }
  }

  @Test
  fun testLineTrimOffset() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      polylineAnnotationManager.lineTrimOffset = expectedValue
      assertEquals(expectedValue, polylineAnnotationManager.lineTrimOffset)
      polylineAnnotationManager.lineTrimOffset = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("line", "line-trim-offset").silentUnwrap(), polylineAnnotationManager.lineTrimOffset)
    }
  }

  @Test
  fun create() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      val annotation = polylineAnnotationManager.create(
        PolylineAnnotationOptions()
          .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      )
      assertEquals(annotation, polylineAnnotationManager.annotations[0])
    }
  }

  @Test
  fun createFromFeature() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      val featureCollection =
        FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))))
      val annotations = polylineAnnotationManager.create(featureCollection.toJson())
      assertEquals(annotations.first(), polylineAnnotationManager.annotations[0])
      val annotations1 = polylineAnnotationManager.create(featureCollection)
      assertEquals(annotations1.first(), polylineAnnotationManager.annotations[1])
    }
  }

  @Test
  fun createList() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      val list = listOf(
        PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
        PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      )
      val annotations = polylineAnnotationManager.create(list)
      assertEquals(annotations[0], polylineAnnotationManager.annotations[0])
      assertEquals(annotations[1], polylineAnnotationManager.annotations[1])
    }
  }

  @Test
  fun update() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      val annotation = polylineAnnotationManager.create(PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))))
      assertEquals(annotation, polylineAnnotationManager.annotations[0])
      annotation.points = listOf(Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 1.0))
      annotation.lineJoin = LineJoin.BEVEL
      polylineAnnotationManager.update(annotation)
      assertEquals(annotation, polylineAnnotationManager.annotations[0])
      assertEquals(LineJoin.BEVEL, annotation.lineJoin)
    }
  }

  @Test
  fun updateList() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
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
  }

  @Test
  fun delete() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
      val annotation = polylineAnnotationManager.create(
        PolylineAnnotationOptions()
          .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      )
      assertEquals(annotation, polylineAnnotationManager.annotations[0])
      polylineAnnotationManager.delete(annotation)
      assertTrue(polylineAnnotationManager.annotations.isEmpty())
    }
  }

  @Test
  fun deleteList() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
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
  }

  @Test
  fun deleteAll() {
    rule.runOnUiThread {
      val polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
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

  fun ActivityScenarioRule<*>.runOnUiThread(block: () -> Unit) {
    this.scenario.onActivity {
      it.runOnUiThread(block)
    }
  }
}
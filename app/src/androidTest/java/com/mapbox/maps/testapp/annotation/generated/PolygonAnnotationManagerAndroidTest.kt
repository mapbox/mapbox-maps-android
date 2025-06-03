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
  fun testFillConstructBridgeGuardRail() {
    rule.runOnUiThread {
      val expectedValue = true
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillConstructBridgeGuardRail = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillConstructBridgeGuardRail)
      polygonAnnotationManager.fillConstructBridgeGuardRail = null
      assertEquals(null, polygonAnnotationManager.fillConstructBridgeGuardRail)
    }
  }

  @Test
  fun testFillElevationReference() {
    rule.runOnUiThread {
      val expectedValue = FillElevationReference.NONE
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillElevationReference = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillElevationReference)
      polygonAnnotationManager.fillElevationReference = null
      val expectedDefaultValue = FillElevationReference.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-elevation-reference").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, polygonAnnotationManager.fillElevationReference)
    }
  }

  @Test
  fun testFillSortKey() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillSortKey = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillSortKey)
      polygonAnnotationManager.fillSortKey = null
      assertEquals(null, polygonAnnotationManager.fillSortKey)
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
  fun testFillBridgeGuardRailColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillBridgeGuardRailColorString = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillBridgeGuardRailColorString)
      polygonAnnotationManager.fillBridgeGuardRailColorString = null
      assertEquals(
        null,
        polygonAnnotationManager.fillBridgeGuardRailColorString
      )
    }
  }

  @Test
  fun testFillColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillColorString = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillColorString)
      polygonAnnotationManager.fillColorString = null
      assertEquals(
        null,
        polygonAnnotationManager.fillColorString
      )
    }
  }

  @Test
  fun testFillEmissiveStrength() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillEmissiveStrength = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillEmissiveStrength)
      polygonAnnotationManager.fillEmissiveStrength = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-emissive-strength").silentUnwrap(), polygonAnnotationManager.fillEmissiveStrength)
    }
  }

  @Test
  fun testFillOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillOpacity = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillOpacity)
      polygonAnnotationManager.fillOpacity = null
      assertEquals(null, polygonAnnotationManager.fillOpacity)
    }
  }

  @Test
  fun testFillOutlineColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillOutlineColorString = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillOutlineColorString)
      polygonAnnotationManager.fillOutlineColorString = null
      assertEquals(
        null,
        polygonAnnotationManager.fillOutlineColorString
      )
    }
  }

  @Test
  fun testFillPattern() {
    rule.runOnUiThread {
      val expectedValue = "abc"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillPattern = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillPattern)
      polygonAnnotationManager.fillPattern = null
      assertEquals(null, polygonAnnotationManager.fillPattern)
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
      val expectedDefaultValue = FillTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("fill", "fill-translate-anchor").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, polygonAnnotationManager.fillTranslateAnchor)
    }
  }

  @Test
  fun testFillTunnelStructureColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillTunnelStructureColorString = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillTunnelStructureColorString)
      polygonAnnotationManager.fillTunnelStructureColorString = null
      assertEquals(
        null,
        polygonAnnotationManager.fillTunnelStructureColorString
      )
    }
  }

  @Test
  fun testFillZOffset() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillZOffset = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillZOffset)
      polygonAnnotationManager.fillZOffset = null
      assertEquals(null, polygonAnnotationManager.fillZOffset)
    }
  }

  @Test
  fun testSlot() {
    rule.runOnUiThread {
      val expectedValue = "abc"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.slot = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.slot)
      polygonAnnotationManager.slot = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("fill", "slot").silentUnwrap(), polygonAnnotationManager.slot)
    }
  }

  @Test
  fun testMaxZoom() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.maxZoom = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.maxZoom)
      polygonAnnotationManager.maxZoom = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("fill", "maxzoom").silentUnwrap(), polygonAnnotationManager.maxZoom)
    }
  }

  @Test
  fun testMinZoom() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.minZoom = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.minZoom)
      polygonAnnotationManager.minZoom = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("fill", "minzoom").silentUnwrap(), polygonAnnotationManager.minZoom)
    }
  }

  @Test
  fun testFillBridgeGuardRailColorUseTheme() {
    rule.runOnUiThread {
      val expectedValue = "default"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillBridgeGuardRailColorUseTheme = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillBridgeGuardRailColorUseTheme)
      polygonAnnotationManager.fillBridgeGuardRailColorUseTheme = null
      assertEquals(null, polygonAnnotationManager.fillBridgeGuardRailColorUseTheme)
    }
  }

  @Test
  fun testFillColorUseTheme() {
    rule.runOnUiThread {
      val expectedValue = "default"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillColorUseTheme = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillColorUseTheme)
      polygonAnnotationManager.fillColorUseTheme = null
      assertEquals(null, polygonAnnotationManager.fillColorUseTheme)
    }
  }

  @Test
  fun testFillOutlineColorUseTheme() {
    rule.runOnUiThread {
      val expectedValue = "default"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillOutlineColorUseTheme = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillOutlineColorUseTheme)
      polygonAnnotationManager.fillOutlineColorUseTheme = null
      assertEquals(null, polygonAnnotationManager.fillOutlineColorUseTheme)
    }
  }

  @Test
  fun testFillTunnelStructureColorUseTheme() {
    rule.runOnUiThread {
      val expectedValue = "default"
      val polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
      polygonAnnotationManager.fillTunnelStructureColorUseTheme = expectedValue
      assertEquals(expectedValue, polygonAnnotationManager.fillTunnelStructureColorUseTheme)
      polygonAnnotationManager.fillTunnelStructureColorUseTheme = null
      assertEquals(null, polygonAnnotationManager.fillTunnelStructureColorUseTheme)
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
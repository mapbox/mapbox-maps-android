// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.generated.SymbolOptions
import com.mapbox.maps.plugin.annotation.generated.createSymbolManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for SymbolManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SymbolManagerAndroidTest : BaseMapTest() {
  override fun initialiseMapView() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        it.setContentView(com.mapbox.maps.testapp.R.layout.generated_test_attribution)
        mapView = it.findViewById(R.id.mapView)
      }
    }
  }

  @Test
  fun testIconAllowOverlap() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconAllowOverlap = testValue
    assertEquals(testValue, symbolManager.iconAllowOverlap)
  }

  @Test
  fun testIconIgnorePlacement() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconIgnorePlacement = testValue
    assertEquals(testValue, symbolManager.iconIgnorePlacement)
  }

  @Test
  fun testIconKeepUpright() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconKeepUpright = testValue
    assertEquals(testValue, symbolManager.iconKeepUpright)
  }

  @Test
  fun testIconOptional() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconOptional = testValue
    assertEquals(testValue, symbolManager.iconOptional)
  }

  @Test
  fun testIconPadding() {
    val testValue = 1.0
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconPadding = testValue
    assertEquals(testValue, symbolManager.iconPadding)
  }

  @Test
  fun testIconPitchAlignment() {
    val testValue = IconPitchAlignment.MAP
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconPitchAlignment = testValue
    assertEquals(testValue, symbolManager.iconPitchAlignment)
  }

  @Test
  fun testIconRotationAlignment() {
    val testValue = IconRotationAlignment.MAP
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconRotationAlignment = testValue
    assertEquals(testValue, symbolManager.iconRotationAlignment)
  }

  @Test
  fun testIconTextFit() {
    val testValue = IconTextFit.NONE
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconTextFit = testValue
    assertEquals(testValue, symbolManager.iconTextFit)
  }

  @Test
  fun testIconTextFitPadding() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconTextFitPadding = testValue
    assertEquals(testValue, symbolManager.iconTextFitPadding)
  }

  @Test
  fun testSymbolAvoidEdges() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.symbolAvoidEdges = testValue
    assertEquals(testValue, symbolManager.symbolAvoidEdges)
  }

  @Test
  fun testSymbolPlacement() {
    val testValue = SymbolPlacement.POINT
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.symbolPlacement = testValue
    assertEquals(testValue, symbolManager.symbolPlacement)
  }

  @Test
  fun testSymbolSpacing() {
    val testValue = 1.0
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.symbolSpacing = testValue
    assertEquals(testValue, symbolManager.symbolSpacing)
  }

  @Test
  fun testTextAllowOverlap() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textAllowOverlap = testValue
    assertEquals(testValue, symbolManager.textAllowOverlap)
  }

  @Test
  fun testTextIgnorePlacement() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textIgnorePlacement = testValue
    assertEquals(testValue, symbolManager.textIgnorePlacement)
  }

  @Test
  fun testTextKeepUpright() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textKeepUpright = testValue
    assertEquals(testValue, symbolManager.textKeepUpright)
  }

  @Test
  fun testTextLineHeight() {
    val testValue = 1.0
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textLineHeight = testValue
    assertEquals(testValue, symbolManager.textLineHeight)
  }

  @Test
  fun testTextMaxAngle() {
    val testValue = 1.0
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textMaxAngle = testValue
    assertEquals(testValue, symbolManager.textMaxAngle)
  }

  @Test
  fun testTextOptional() {
    val testValue = true
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textOptional = testValue
    assertEquals(testValue, symbolManager.textOptional)
  }

  @Test
  fun testTextPadding() {
    val testValue = 1.0
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textPadding = testValue
    assertEquals(testValue, symbolManager.textPadding)
  }

  @Test
  fun testTextPitchAlignment() {
    val testValue = TextPitchAlignment.MAP
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textPitchAlignment = testValue
    assertEquals(testValue, symbolManager.textPitchAlignment)
  }

  @Test
  fun testTextRotationAlignment() {
    val testValue = TextRotationAlignment.MAP
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textRotationAlignment = testValue
    assertEquals(testValue, symbolManager.textRotationAlignment)
  }

  @Test
  fun testTextVariableAnchor() {
    val testValue = listOf("center", "left")
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textVariableAnchor = testValue
    assertEquals(testValue, symbolManager.textVariableAnchor)
  }

  @Test
  fun testTextWritingMode() {
    val testValue = listOf("horizontal", "vertical")
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textWritingMode = testValue
    assertEquals(testValue, symbolManager.textWritingMode)
  }

  @Test
  fun testIconTranslate() {
    val testValue = listOf(0.0, 1.0)
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconTranslate = testValue
    assertEquals(testValue, symbolManager.iconTranslate)
  }

  @Test
  fun testIconTranslateAnchor() {
    val testValue = IconTranslateAnchor.MAP
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.iconTranslateAnchor = testValue
    assertEquals(testValue, symbolManager.iconTranslateAnchor)
  }

  @Test
  fun testTextTranslate() {
    val testValue = listOf(0.0, 1.0)
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textTranslate = testValue
    assertEquals(testValue, symbolManager.textTranslate)
  }

  @Test
  fun testTextTranslateAnchor() {
    val testValue = TextTranslateAnchor.MAP
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    symbolManager.textTranslateAnchor = testValue
    assertEquals(testValue, symbolManager.textTranslateAnchor)
  }

  @Test
  fun create() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val annotation = symbolManager.create(
      SymbolOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, symbolManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val annotations = symbolManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), symbolManager.annotations[0])
    val annotations1 = symbolManager.create(featureCollection)
    assertEquals(annotations1.first(), symbolManager.annotations[1])
  }

  @Test
  fun createList() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val list = listOf(
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = symbolManager.create(list)
    assertEquals(annotations[0], symbolManager.annotations[0])
    assertEquals(annotations[1], symbolManager.annotations[1])
  }

  @Test
  fun update() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val annotation = symbolManager.create(SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, symbolManager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    symbolManager.update(annotation)
    assertEquals(annotation, symbolManager.annotations[0])
  }

  @Test
  fun updateList() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val list = listOf(
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = symbolManager.create(list)
    assertEquals(annotations[0], symbolManager.annotations[0])
    assertEquals(annotations[1], symbolManager.annotations[1])
    annotations[0].point = Point.fromLngLat(1.0, 1.0)
    annotations[1].point = Point.fromLngLat(1.0, 1.0)
    symbolManager.update(annotations)
    assertEquals(annotations[0], symbolManager.annotations[0])
    assertEquals(annotations[1], symbolManager.annotations[1])
  }

  @Test
  fun delete() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val annotation = symbolManager.create(
      SymbolOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, symbolManager.annotations[0])
    symbolManager.delete(annotation)
    assertTrue(symbolManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val list = listOf(
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = symbolManager.create(list)
    assertEquals(annotations[0], symbolManager.annotations[0])
    assertEquals(annotations[1], symbolManager.annotations[1])

    symbolManager.delete(annotations)
    assertTrue(symbolManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val symbolManager = mapView.getAnnotationPlugin().createSymbolManager(mapView)
    val list = listOf(
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      SymbolOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = symbolManager.create(list)
    assertEquals(annotations[0], symbolManager.annotations[0])
    assertEquals(annotations[1], symbolManager.annotations[1])

    symbolManager.deleteAll()
    assertTrue(symbolManager.annotations.isEmpty())
  }
}
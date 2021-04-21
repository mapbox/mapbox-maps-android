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
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for PointAnnotationManager
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class PointAnnotationManagerAndroidTest : BaseMapTest() {
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
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconAllowOverlap = testValue
    assertEquals(testValue, pointAnnotationManager.iconAllowOverlap)
  }

  @Test
  fun testIconIgnorePlacement() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconIgnorePlacement = testValue
    assertEquals(testValue, pointAnnotationManager.iconIgnorePlacement)
  }

  @Test
  fun testIconKeepUpright() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconKeepUpright = testValue
    assertEquals(testValue, pointAnnotationManager.iconKeepUpright)
  }

  @Test
  fun testIconOptional() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconOptional = testValue
    assertEquals(testValue, pointAnnotationManager.iconOptional)
  }

  @Test
  fun testIconPadding() {
    val testValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconPadding = testValue
    assertEquals(testValue, pointAnnotationManager.iconPadding)
  }

  @Test
  fun testIconPitchAlignment() {
    val testValue = IconPitchAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconPitchAlignment = testValue
    assertEquals(testValue, pointAnnotationManager.iconPitchAlignment)
  }

  @Test
  fun testIconRotationAlignment() {
    val testValue = IconRotationAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconRotationAlignment = testValue
    assertEquals(testValue, pointAnnotationManager.iconRotationAlignment)
  }

  @Test
  fun testIconTextFit() {
    val testValue = IconTextFit.NONE
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTextFit = testValue
    assertEquals(testValue, pointAnnotationManager.iconTextFit)
  }

  @Test
  fun testIconTextFitPadding() {
    val testValue = listOf(0.0, 1.0, 2.0, 3.0)
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTextFitPadding = testValue
    assertEquals(testValue, pointAnnotationManager.iconTextFitPadding)
  }

  @Test
  fun testSymbolAvoidEdges() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolAvoidEdges = testValue
    assertEquals(testValue, pointAnnotationManager.symbolAvoidEdges)
  }

  @Test
  fun testSymbolPlacement() {
    val testValue = SymbolPlacement.POINT
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolPlacement = testValue
    assertEquals(testValue, pointAnnotationManager.symbolPlacement)
  }

  @Test
  fun testSymbolSpacing() {
    val testValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolSpacing = testValue
    assertEquals(testValue, pointAnnotationManager.symbolSpacing)
  }

  @Test
  fun testTextAllowOverlap() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textAllowOverlap = testValue
    assertEquals(testValue, pointAnnotationManager.textAllowOverlap)
  }

  @Test
  fun testTextIgnorePlacement() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textIgnorePlacement = testValue
    assertEquals(testValue, pointAnnotationManager.textIgnorePlacement)
  }

  @Test
  fun testTextKeepUpright() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textKeepUpright = testValue
    assertEquals(testValue, pointAnnotationManager.textKeepUpright)
  }

  @Test
  fun testTextLineHeight() {
    val testValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textLineHeight = testValue
    assertEquals(testValue, pointAnnotationManager.textLineHeight)
  }

  @Test
  fun testTextMaxAngle() {
    val testValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textMaxAngle = testValue
    assertEquals(testValue, pointAnnotationManager.textMaxAngle)
  }

  @Test
  fun testTextOptional() {
    val testValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textOptional = testValue
    assertEquals(testValue, pointAnnotationManager.textOptional)
  }

  @Test
  fun testTextPadding() {
    val testValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textPadding = testValue
    assertEquals(testValue, pointAnnotationManager.textPadding)
  }

  @Test
  fun testTextPitchAlignment() {
    val testValue = TextPitchAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textPitchAlignment = testValue
    assertEquals(testValue, pointAnnotationManager.textPitchAlignment)
  }

  @Test
  fun testTextRotationAlignment() {
    val testValue = TextRotationAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textRotationAlignment = testValue
    assertEquals(testValue, pointAnnotationManager.textRotationAlignment)
  }

  @Test
  fun testTextVariableAnchor() {
    val testValue = listOf("center", "left")
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textVariableAnchor = testValue
    assertEquals(testValue, pointAnnotationManager.textVariableAnchor)
  }

  @Test
  fun testTextWritingMode() {
    val testValue = listOf("horizontal", "vertical")
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textWritingMode = testValue
    assertEquals(testValue, pointAnnotationManager.textWritingMode)
  }

  @Test
  fun testIconTranslate() {
    val testValue = listOf(0.0, 1.0)
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTranslate = testValue
    assertEquals(testValue, pointAnnotationManager.iconTranslate)
  }

  @Test
  fun testIconTranslateAnchor() {
    val testValue = IconTranslateAnchor.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTranslateAnchor = testValue
    assertEquals(testValue, pointAnnotationManager.iconTranslateAnchor)
  }

  @Test
  fun testTextTranslate() {
    val testValue = listOf(0.0, 1.0)
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textTranslate = testValue
    assertEquals(testValue, pointAnnotationManager.textTranslate)
  }

  @Test
  fun testTextTranslateAnchor() {
    val testValue = TextTranslateAnchor.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textTranslateAnchor = testValue
    assertEquals(testValue, pointAnnotationManager.textTranslateAnchor)
  }

  @Test
  fun create() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val annotation = pointAnnotationManager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, pointAnnotationManager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val annotations = pointAnnotationManager.create(featureCollection.toJson())
    assertEquals(annotations.first(), pointAnnotationManager.annotations[0])
    val annotations1 = pointAnnotationManager.create(featureCollection)
    assertEquals(annotations1.first(), pointAnnotationManager.annotations[1])
  }

  @Test
  fun createList() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val list = listOf(
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = pointAnnotationManager.create(list)
    assertEquals(annotations[0], pointAnnotationManager.annotations[0])
    assertEquals(annotations[1], pointAnnotationManager.annotations[1])
  }

  @Test
  fun update() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val annotation = pointAnnotationManager.create(PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, pointAnnotationManager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    pointAnnotationManager.update(annotation)
    assertEquals(annotation, pointAnnotationManager.annotations[0])
  }

  @Test
  fun updateList() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val list = listOf(
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = pointAnnotationManager.create(list)
    assertEquals(annotations[0], pointAnnotationManager.annotations[0])
    assertEquals(annotations[1], pointAnnotationManager.annotations[1])
    annotations[0].point = Point.fromLngLat(1.0, 1.0)
    annotations[1].point = Point.fromLngLat(1.0, 1.0)
    pointAnnotationManager.update(annotations)
    assertEquals(annotations[0], pointAnnotationManager.annotations[0])
    assertEquals(annotations[1], pointAnnotationManager.annotations[1])
  }

  @Test
  fun delete() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val annotation = pointAnnotationManager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, pointAnnotationManager.annotations[0])
    pointAnnotationManager.delete(annotation)
    assertTrue(pointAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val list = listOf(
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = pointAnnotationManager.create(list)
    assertEquals(annotations[0], pointAnnotationManager.annotations[0])
    assertEquals(annotations[1], pointAnnotationManager.annotations[1])

    pointAnnotationManager.delete(annotations)
    assertTrue(pointAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val list = listOf(
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = pointAnnotationManager.create(list)
    assertEquals(annotations[0], pointAnnotationManager.annotations[0])
    assertEquals(annotations[1], pointAnnotationManager.annotations[1])

    pointAnnotationManager.deleteAll()
    assertTrue(pointAnnotationManager.annotations.isEmpty())
  }
}
// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.R
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.testapp.BaseMapTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

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
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconAllowOverlap = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconAllowOverlap)
    pointAnnotationManager.iconAllowOverlap = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap").silentUnwrap(), pointAnnotationManager.iconAllowOverlap)
  }

  @Test
  fun testIconIgnorePlacement() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconIgnorePlacement = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconIgnorePlacement)
    pointAnnotationManager.iconIgnorePlacement = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement").silentUnwrap(), pointAnnotationManager.iconIgnorePlacement)
  }

  @Test
  fun testIconKeepUpright() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconKeepUpright = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconKeepUpright)
    pointAnnotationManager.iconKeepUpright = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright").silentUnwrap(), pointAnnotationManager.iconKeepUpright)
  }

  @Test
  fun testIconOptional() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconOptional = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconOptional)
    pointAnnotationManager.iconOptional = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional").silentUnwrap(), pointAnnotationManager.iconOptional)
  }

  @Test
  fun testIconPadding() {
    val expectedValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconPadding = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconPadding)
    pointAnnotationManager.iconPadding = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding").silentUnwrap(), pointAnnotationManager.iconPadding)
  }

  @Test
  fun testIconPitchAlignment() {
    val expectedValue = IconPitchAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconPitchAlignment = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconPitchAlignment)
    pointAnnotationManager.iconPitchAlignment = null
    val expectedDefaultValue = IconPitchAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.iconPitchAlignment)
  }

  @Test
  fun testIconRotationAlignment() {
    val expectedValue = IconRotationAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconRotationAlignment = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconRotationAlignment)
    pointAnnotationManager.iconRotationAlignment = null
    val expectedDefaultValue = IconRotationAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.iconRotationAlignment)
  }

  @Test
  fun testIconTextFit() {
    val expectedValue = IconTextFit.NONE
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTextFit = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconTextFit)
    pointAnnotationManager.iconTextFit = null
    val expectedDefaultValue = IconTextFit.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.iconTextFit)
  }

  @Test
  fun testIconTextFitPadding() {
    val expectedValue = listOf(0.0, 1.0, 2.0, 3.0)
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTextFitPadding = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconTextFitPadding)
    pointAnnotationManager.iconTextFitPadding = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit-padding").silentUnwrap(), pointAnnotationManager.iconTextFitPadding)
  }

  @Test
  fun testSymbolAvoidEdges() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolAvoidEdges = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.symbolAvoidEdges)
    pointAnnotationManager.symbolAvoidEdges = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges").silentUnwrap(), pointAnnotationManager.symbolAvoidEdges)
  }

  @Test
  fun testSymbolPlacement() {
    val expectedValue = SymbolPlacement.POINT
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolPlacement = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.symbolPlacement)
    pointAnnotationManager.symbolPlacement = null
    val expectedDefaultValue = SymbolPlacement.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.symbolPlacement)
  }

  @Test
  fun testSymbolSpacing() {
    val expectedValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolSpacing = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.symbolSpacing)
    pointAnnotationManager.symbolSpacing = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing").silentUnwrap(), pointAnnotationManager.symbolSpacing)
  }

  @Test
  fun testSymbolZOrder() {
    val expectedValue = SymbolZOrder.AUTO
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.symbolZOrder = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.symbolZOrder)
    pointAnnotationManager.symbolZOrder = null
    val expectedDefaultValue = SymbolZOrder.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.symbolZOrder)
  }

  @Test
  fun testTextAllowOverlap() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textAllowOverlap = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textAllowOverlap)
    pointAnnotationManager.textAllowOverlap = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap").silentUnwrap(), pointAnnotationManager.textAllowOverlap)
  }

  @Test
  fun testTextFont() {
    val expectedValue = listOf("a", "b", "c")
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textFont = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textFont)
    pointAnnotationManager.textFont = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font").silentUnwrap(), pointAnnotationManager.textFont)
  }

  @Test
  fun testTextIgnorePlacement() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textIgnorePlacement = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textIgnorePlacement)
    pointAnnotationManager.textIgnorePlacement = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement").silentUnwrap(), pointAnnotationManager.textIgnorePlacement)
  }

  @Test
  fun testTextKeepUpright() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textKeepUpright = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textKeepUpright)
    pointAnnotationManager.textKeepUpright = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright").silentUnwrap(), pointAnnotationManager.textKeepUpright)
  }

  @Test
  fun testTextLineHeight() {
    val expectedValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textLineHeight = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textLineHeight)
    pointAnnotationManager.textLineHeight = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-line-height").silentUnwrap(), pointAnnotationManager.textLineHeight)
  }

  @Test
  fun testTextMaxAngle() {
    val expectedValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textMaxAngle = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textMaxAngle)
    pointAnnotationManager.textMaxAngle = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle").silentUnwrap(), pointAnnotationManager.textMaxAngle)
  }

  @Test
  fun testTextOptional() {
    val expectedValue = true
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textOptional = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textOptional)
    pointAnnotationManager.textOptional = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional").silentUnwrap(), pointAnnotationManager.textOptional)
  }

  @Test
  fun testTextPadding() {
    val expectedValue = 1.0
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textPadding = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textPadding)
    pointAnnotationManager.textPadding = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding").silentUnwrap(), pointAnnotationManager.textPadding)
  }

  @Test
  fun testTextPitchAlignment() {
    val expectedValue = TextPitchAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textPitchAlignment = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textPitchAlignment)
    pointAnnotationManager.textPitchAlignment = null
    val expectedDefaultValue = TextPitchAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.textPitchAlignment)
  }

  @Test
  fun testTextRotationAlignment() {
    val expectedValue = TextRotationAlignment.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textRotationAlignment = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textRotationAlignment)
    pointAnnotationManager.textRotationAlignment = null
    val expectedDefaultValue = TextRotationAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.textRotationAlignment)
  }

  @Test
  fun testTextVariableAnchor() {
    val expectedValue = listOf("center", "left")
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textVariableAnchor = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textVariableAnchor)
    pointAnnotationManager.textVariableAnchor = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor").silentUnwrap(), pointAnnotationManager.textVariableAnchor)
  }

  @Test
  fun testTextWritingMode() {
    val expectedValue = listOf("horizontal", "vertical")
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textWritingMode = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textWritingMode)
    pointAnnotationManager.textWritingMode = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode").silentUnwrap(), pointAnnotationManager.textWritingMode)
  }

  @Test
  fun testIconTranslate() {
    val expectedValue = listOf(0.0, 1.0)
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTranslate = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconTranslate)
    pointAnnotationManager.iconTranslate = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate").silentUnwrap(), pointAnnotationManager.iconTranslate)
  }

  @Test
  fun testIconTranslateAnchor() {
    val expectedValue = IconTranslateAnchor.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.iconTranslateAnchor = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.iconTranslateAnchor)
    pointAnnotationManager.iconTranslateAnchor = null
    val expectedDefaultValue = IconTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.iconTranslateAnchor)
  }

  @Test
  fun testTextTranslate() {
    val expectedValue = listOf(0.0, 1.0)
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textTranslate = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textTranslate)
    pointAnnotationManager.textTranslate = null
    assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate").silentUnwrap(), pointAnnotationManager.textTranslate)
  }

  @Test
  fun testTextTranslateAnchor() {
    val expectedValue = TextTranslateAnchor.MAP
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    pointAnnotationManager.textTranslateAnchor = expectedValue
    assertEquals(expectedValue, pointAnnotationManager.textTranslateAnchor)
    pointAnnotationManager.textTranslateAnchor = null
    val expectedDefaultValue = TextTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor").silentUnwrap<String>()!!.toUpperCase(Locale.US).replace('-', '_'))
    assertEquals(expectedDefaultValue, pointAnnotationManager.textTranslateAnchor)
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
    annotation.iconAnchor = IconAnchor.CENTER
    annotation.textAnchor = TextAnchor.CENTER
    annotation.textJustify = TextJustify.AUTO
    annotation.textTransform = TextTransform.NONE
    pointAnnotationManager.update(annotation)
    assertEquals(annotation, pointAnnotationManager.annotations[0])
    assertEquals(IconAnchor.CENTER, annotation.iconAnchor)
    assertEquals(TextAnchor.CENTER, annotation.textAnchor)
    assertEquals(TextJustify.AUTO, annotation.textJustify)
    assertEquals(TextTransform.NONE, annotation.textTransform)
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
  fun deleteAndAdd() {
    val pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    val annotation = pointAnnotationManager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, pointAnnotationManager.annotations[0])
    pointAnnotationManager.delete(annotation)
    assertTrue(pointAnnotationManager.annotations.isEmpty())
    pointAnnotationManager.addAnnotation(annotation)
    assertEquals(annotation, pointAnnotationManager.annotations[0])
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
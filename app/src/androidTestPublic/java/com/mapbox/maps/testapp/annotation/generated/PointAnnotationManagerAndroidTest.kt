// This file is generated.

package com.mapbox.maps.testapp.annotation.generated

import androidx.test.ext.junit.rules.ActivityScenarioRule
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
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconAllowOverlap = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconAllowOverlap)
      pointAnnotationManager.iconAllowOverlap = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap").silentUnwrap(), pointAnnotationManager.iconAllowOverlap)
    }
  }

  @Test
  fun testIconIgnorePlacement() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconIgnorePlacement = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconIgnorePlacement)
      pointAnnotationManager.iconIgnorePlacement = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement").silentUnwrap(), pointAnnotationManager.iconIgnorePlacement)
    }
  }

  @Test
  fun testIconKeepUpright() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconKeepUpright = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconKeepUpright)
      pointAnnotationManager.iconKeepUpright = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright").silentUnwrap(), pointAnnotationManager.iconKeepUpright)
    }
  }

  @Test
  fun testIconOptional() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconOptional = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconOptional)
      pointAnnotationManager.iconOptional = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional").silentUnwrap(), pointAnnotationManager.iconOptional)
    }
  }

  @Test
  fun testIconPadding() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconPadding = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconPadding)
      pointAnnotationManager.iconPadding = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding").silentUnwrap(), pointAnnotationManager.iconPadding)
    }
  }

  @Test
  fun testIconPitchAlignment() {
    rule.runOnUiThread {
      val expectedValue = IconPitchAlignment.MAP
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconPitchAlignment = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconPitchAlignment)
      pointAnnotationManager.iconPitchAlignment = null
      val expectedDefaultValue = IconPitchAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.iconPitchAlignment)
    }
  }

  @Test
  fun testIconRotationAlignment() {
    rule.runOnUiThread {
      val expectedValue = IconRotationAlignment.MAP
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconRotationAlignment = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconRotationAlignment)
      pointAnnotationManager.iconRotationAlignment = null
      val expectedDefaultValue = IconRotationAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.iconRotationAlignment)
    }
  }

  @Test
  fun testSymbolAvoidEdges() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolAvoidEdges = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolAvoidEdges)
      pointAnnotationManager.symbolAvoidEdges = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges").silentUnwrap(), pointAnnotationManager.symbolAvoidEdges)
    }
  }

  @Test
  fun testSymbolPlacement() {
    rule.runOnUiThread {
      val expectedValue = SymbolPlacement.POINT
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolPlacement = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolPlacement)
      pointAnnotationManager.symbolPlacement = null
      val expectedDefaultValue = SymbolPlacement.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.symbolPlacement)
    }
  }

  @Test
  fun testSymbolSpacing() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolSpacing = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolSpacing)
      pointAnnotationManager.symbolSpacing = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing").silentUnwrap(), pointAnnotationManager.symbolSpacing)
    }
  }

  @Test
  fun testSymbolZOrder() {
    rule.runOnUiThread {
      val expectedValue = SymbolZOrder.AUTO
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolZOrder = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolZOrder)
      pointAnnotationManager.symbolZOrder = null
      val expectedDefaultValue = SymbolZOrder.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.symbolZOrder)
    }
  }

  @Test
  fun testTextAllowOverlap() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textAllowOverlap = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textAllowOverlap)
      pointAnnotationManager.textAllowOverlap = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap").silentUnwrap(), pointAnnotationManager.textAllowOverlap)
    }
  }

  @Test
  fun testTextFont() {
    rule.runOnUiThread {
      val expectedValue = listOf("a", "b", "c")
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textFont = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textFont)
      pointAnnotationManager.textFont = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font").silentUnwrap(), pointAnnotationManager.textFont)
    }
  }

  @Test
  fun testTextIgnorePlacement() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textIgnorePlacement = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textIgnorePlacement)
      pointAnnotationManager.textIgnorePlacement = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement").silentUnwrap(), pointAnnotationManager.textIgnorePlacement)
    }
  }

  @Test
  fun testTextKeepUpright() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textKeepUpright = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textKeepUpright)
      pointAnnotationManager.textKeepUpright = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright").silentUnwrap(), pointAnnotationManager.textKeepUpright)
    }
  }

  @Test
  fun testTextMaxAngle() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textMaxAngle = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textMaxAngle)
      pointAnnotationManager.textMaxAngle = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle").silentUnwrap(), pointAnnotationManager.textMaxAngle)
    }
  }

  @Test
  fun testTextOptional() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textOptional = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textOptional)
      pointAnnotationManager.textOptional = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional").silentUnwrap(), pointAnnotationManager.textOptional)
    }
  }

  @Test
  fun testTextPadding() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textPadding = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textPadding)
      pointAnnotationManager.textPadding = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding").silentUnwrap(), pointAnnotationManager.textPadding)
    }
  }

  @Test
  fun testTextPitchAlignment() {
    rule.runOnUiThread {
      val expectedValue = TextPitchAlignment.MAP
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textPitchAlignment = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textPitchAlignment)
      pointAnnotationManager.textPitchAlignment = null
      val expectedDefaultValue = TextPitchAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.textPitchAlignment)
    }
  }

  @Test
  fun testTextRotationAlignment() {
    rule.runOnUiThread {
      val expectedValue = TextRotationAlignment.MAP
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textRotationAlignment = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textRotationAlignment)
      pointAnnotationManager.textRotationAlignment = null
      val expectedDefaultValue = TextRotationAlignment.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.textRotationAlignment)
    }
  }

  @Test
  fun testTextVariableAnchor() {
    rule.runOnUiThread {
      val expectedValue = listOf("center", "left")
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textVariableAnchor = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textVariableAnchor)
      pointAnnotationManager.textVariableAnchor = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor").silentUnwrap(), pointAnnotationManager.textVariableAnchor)
    }
  }

  @Test
  fun testTextWritingMode() {
    rule.runOnUiThread {
      val expectedValue = listOf("horizontal", "vertical")
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textWritingMode = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textWritingMode)
      pointAnnotationManager.textWritingMode = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode").silentUnwrap(), pointAnnotationManager.textWritingMode)
    }
  }

  @Test
  fun testIconTranslate() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconTranslate = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconTranslate)
      pointAnnotationManager.iconTranslate = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate").silentUnwrap(), pointAnnotationManager.iconTranslate)
    }
  }

  @Test
  fun testIconTranslateAnchor() {
    rule.runOnUiThread {
      val expectedValue = IconTranslateAnchor.MAP
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconTranslateAnchor = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconTranslateAnchor)
      pointAnnotationManager.iconTranslateAnchor = null
      val expectedDefaultValue = IconTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.iconTranslateAnchor)
    }
  }

  @Test
  fun testTextTranslate() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textTranslate = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textTranslate)
      pointAnnotationManager.textTranslate = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate").silentUnwrap(), pointAnnotationManager.textTranslate)
    }
  }

  @Test
  fun testTextTranslateAnchor() {
    rule.runOnUiThread {
      val expectedValue = TextTranslateAnchor.MAP
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textTranslateAnchor = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textTranslateAnchor)
      pointAnnotationManager.textTranslateAnchor = null
      val expectedDefaultValue = TextTranslateAnchor.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.textTranslateAnchor)
    }
  }

  @Test
  fun create() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      val annotation = pointAnnotationManager.create(
        PointAnnotationOptions()
          .withPoint(Point.fromLngLat(0.0, 0.0))
      )
      assertEquals(annotation, pointAnnotationManager.annotations[0])
    }
  }

  @Test
  fun createFromFeature() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      val featureCollection =
        FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
      val annotations = pointAnnotationManager.create(featureCollection.toJson())
      assertEquals(annotations.first(), pointAnnotationManager.annotations[0])
      val annotations1 = pointAnnotationManager.create(featureCollection)
      assertEquals(annotations1.first(), pointAnnotationManager.annotations[1])
    }
  }

  @Test
  fun createList() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      val list = listOf(
        PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
        PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
      )
      val annotations = pointAnnotationManager.create(list)
      assertEquals(annotations[0], pointAnnotationManager.annotations[0])
      assertEquals(annotations[1], pointAnnotationManager.annotations[1])
    }
  }

  @Test
  fun update() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      val annotation = pointAnnotationManager.create(PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
      assertEquals(annotation, pointAnnotationManager.annotations[0])
      annotation.point = Point.fromLngLat(1.0, 1.0)
      annotation.iconAnchor = IconAnchor.CENTER
      annotation.iconTextFit = IconTextFit.NONE
      annotation.textAnchor = TextAnchor.CENTER
      annotation.textJustify = TextJustify.AUTO
      annotation.textTransform = TextTransform.NONE
      pointAnnotationManager.update(annotation)
      assertEquals(annotation, pointAnnotationManager.annotations[0])
      assertEquals(IconAnchor.CENTER, annotation.iconAnchor)
      assertEquals(IconTextFit.NONE, annotation.iconTextFit)
      assertEquals(TextAnchor.CENTER, annotation.textAnchor)
      assertEquals(TextJustify.AUTO, annotation.textJustify)
      assertEquals(TextTransform.NONE, annotation.textTransform)
    }
  }

  @Test
  fun updateList() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
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
  }

  @Test
  fun delete() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      val annotation = pointAnnotationManager.create(
        PointAnnotationOptions()
          .withPoint(Point.fromLngLat(0.0, 0.0))
      )
      assertEquals(annotation, pointAnnotationManager.annotations[0])
      pointAnnotationManager.delete(annotation)
      assertTrue(pointAnnotationManager.annotations.isEmpty())
    }
  }

  @Test
  fun deleteList() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
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
  }

  @Test
  fun deleteAll() {
    rule.runOnUiThread {
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
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

  fun ActivityScenarioRule<*>.runOnUiThread(block: () -> Unit) {
    this.scenario.onActivity {
      it.runOnUiThread(block)
    }
  }
}
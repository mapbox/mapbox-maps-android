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
  fun testIconAnchor() {
    rule.runOnUiThread {
      val expectedValue = IconAnchor.CENTER
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconAnchor = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconAnchor)
      pointAnnotationManager.iconAnchor = null
      assertEquals(null, pointAnnotationManager.iconAnchor)
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
  fun testIconImage() {
    rule.runOnUiThread {
      val expectedValue = "abc"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconImage = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconImage)
      pointAnnotationManager.iconImage = null
      assertEquals(null, pointAnnotationManager.iconImage)
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
  fun testIconOffset() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconOffset = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconOffset)
      pointAnnotationManager.iconOffset = null
      assertEquals(null, pointAnnotationManager.iconOffset)
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
  fun testIconRotate() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconRotate = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconRotate)
      pointAnnotationManager.iconRotate = null
      assertEquals(null, pointAnnotationManager.iconRotate)
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
  fun testIconSize() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconSize = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconSize)
      pointAnnotationManager.iconSize = null
      assertEquals(null, pointAnnotationManager.iconSize)
    }
  }

  @Test
  fun testIconSizeScaleRange() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconSizeScaleRange = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconSizeScaleRange)
      pointAnnotationManager.iconSizeScaleRange = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size-scale-range").silentUnwrap(), pointAnnotationManager.iconSizeScaleRange)
    }
  }

  @Test
  fun testIconTextFit() {
    rule.runOnUiThread {
      val expectedValue = IconTextFit.NONE
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconTextFit = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconTextFit)
      pointAnnotationManager.iconTextFit = null
      assertEquals(null, pointAnnotationManager.iconTextFit)
    }
  }

  @Test
  fun testIconTextFitPadding() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0, 2.0, 3.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconTextFitPadding = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconTextFitPadding)
      pointAnnotationManager.iconTextFitPadding = null
      assertEquals(null, pointAnnotationManager.iconTextFitPadding)
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
  fun testSymbolElevationReference() {
    rule.runOnUiThread {
      val expectedValue = SymbolElevationReference.SEA
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolElevationReference = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolElevationReference)
      pointAnnotationManager.symbolElevationReference = null
      val expectedDefaultValue = SymbolElevationReference.valueOf(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-elevation-reference").silentUnwrap<String>()!!.uppercase(Locale.US).replace('-', '_'))
      assertEquals(expectedDefaultValue, pointAnnotationManager.symbolElevationReference)
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
  fun testSymbolSortKey() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolSortKey = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolSortKey)
      pointAnnotationManager.symbolSortKey = null
      assertEquals(null, pointAnnotationManager.symbolSortKey)
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
  fun testSymbolZElevate() {
    rule.runOnUiThread {
      val expectedValue = true
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolZElevate = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolZElevate)
      pointAnnotationManager.symbolZElevate = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-elevate").silentUnwrap(), pointAnnotationManager.symbolZElevate)
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
  fun testTextAnchor() {
    rule.runOnUiThread {
      val expectedValue = TextAnchor.CENTER
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textAnchor = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textAnchor)
      pointAnnotationManager.textAnchor = null
      assertEquals(null, pointAnnotationManager.textAnchor)
    }
  }

  @Test
  fun testTextField() {
    rule.runOnUiThread {
      val expectedValue = "test text"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textField = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textField)
      pointAnnotationManager.textField = null
      assertEquals(null, pointAnnotationManager.textField)
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
  fun testTextJustify() {
    rule.runOnUiThread {
      val expectedValue = TextJustify.AUTO
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textJustify = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textJustify)
      pointAnnotationManager.textJustify = null
      assertEquals(null, pointAnnotationManager.textJustify)
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
  fun testTextLetterSpacing() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textLetterSpacing = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textLetterSpacing)
      pointAnnotationManager.textLetterSpacing = null
      assertEquals(null, pointAnnotationManager.textLetterSpacing)
    }
  }

  @Test
  fun testTextLineHeight() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textLineHeight = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textLineHeight)
      pointAnnotationManager.textLineHeight = null
      assertEquals(null, pointAnnotationManager.textLineHeight)
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
  fun testTextMaxWidth() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textMaxWidth = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textMaxWidth)
      pointAnnotationManager.textMaxWidth = null
      assertEquals(null, pointAnnotationManager.textMaxWidth)
    }
  }

  @Test
  fun testTextOffset() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textOffset = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textOffset)
      pointAnnotationManager.textOffset = null
      assertEquals(null, pointAnnotationManager.textOffset)
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
  fun testTextRadialOffset() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textRadialOffset = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textRadialOffset)
      pointAnnotationManager.textRadialOffset = null
      assertEquals(null, pointAnnotationManager.textRadialOffset)
    }
  }

  @Test
  fun testTextRotate() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textRotate = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textRotate)
      pointAnnotationManager.textRotate = null
      assertEquals(null, pointAnnotationManager.textRotate)
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
  fun testTextSize() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textSize = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textSize)
      pointAnnotationManager.textSize = null
      assertEquals(null, pointAnnotationManager.textSize)
    }
  }

  @Test
  fun testTextSizeScaleRange() {
    rule.runOnUiThread {
      val expectedValue = listOf(0.0, 1.0)
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textSizeScaleRange = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textSizeScaleRange)
      pointAnnotationManager.textSizeScaleRange = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size-scale-range").silentUnwrap(), pointAnnotationManager.textSizeScaleRange)
    }
  }

  @Test
  fun testTextTransform() {
    rule.runOnUiThread {
      val expectedValue = TextTransform.NONE
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textTransform = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textTransform)
      pointAnnotationManager.textTransform = null
      assertEquals(null, pointAnnotationManager.textTransform)
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
  fun testIconColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconColorString = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconColorString)
      pointAnnotationManager.iconColorString = null
      assertEquals(
        null,
        pointAnnotationManager.iconColorString
      )
    }
  }

  @Test
  fun testIconColorSaturation() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconColorSaturation = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconColorSaturation)
      pointAnnotationManager.iconColorSaturation = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color-saturation").silentUnwrap(), pointAnnotationManager.iconColorSaturation)
    }
  }

  @Test
  fun testIconEmissiveStrength() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconEmissiveStrength = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconEmissiveStrength)
      pointAnnotationManager.iconEmissiveStrength = null
      assertEquals(null, pointAnnotationManager.iconEmissiveStrength)
    }
  }

  @Test
  fun testIconHaloBlur() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconHaloBlur = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconHaloBlur)
      pointAnnotationManager.iconHaloBlur = null
      assertEquals(null, pointAnnotationManager.iconHaloBlur)
    }
  }

  @Test
  fun testIconHaloColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconHaloColorString = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconHaloColorString)
      pointAnnotationManager.iconHaloColorString = null
      assertEquals(
        null,
        pointAnnotationManager.iconHaloColorString
      )
    }
  }

  @Test
  fun testIconHaloWidth() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconHaloWidth = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconHaloWidth)
      pointAnnotationManager.iconHaloWidth = null
      assertEquals(null, pointAnnotationManager.iconHaloWidth)
    }
  }

  @Test
  fun testIconImageCrossFade() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconImageCrossFade = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconImageCrossFade)
      pointAnnotationManager.iconImageCrossFade = null
      assertEquals(null, pointAnnotationManager.iconImageCrossFade)
    }
  }

  @Test
  fun testIconOcclusionOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconOcclusionOpacity = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconOcclusionOpacity)
      pointAnnotationManager.iconOcclusionOpacity = null
      assertEquals(null, pointAnnotationManager.iconOcclusionOpacity)
    }
  }

  @Test
  fun testIconOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.iconOpacity = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.iconOpacity)
      pointAnnotationManager.iconOpacity = null
      assertEquals(null, pointAnnotationManager.iconOpacity)
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
  fun testSymbolZOffset() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.symbolZOffset = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.symbolZOffset)
      pointAnnotationManager.symbolZOffset = null
      assertEquals(null, pointAnnotationManager.symbolZOffset)
    }
  }

  @Test
  fun testTextColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textColorString = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textColorString)
      pointAnnotationManager.textColorString = null
      assertEquals(
        null,
        pointAnnotationManager.textColorString
      )
    }
  }

  @Test
  fun testTextEmissiveStrength() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textEmissiveStrength = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textEmissiveStrength)
      pointAnnotationManager.textEmissiveStrength = null
      assertEquals(null, pointAnnotationManager.textEmissiveStrength)
    }
  }

  @Test
  fun testTextHaloBlur() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textHaloBlur = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textHaloBlur)
      pointAnnotationManager.textHaloBlur = null
      assertEquals(null, pointAnnotationManager.textHaloBlur)
    }
  }

  @Test
  fun testTextHaloColor() {
    rule.runOnUiThread {
      val expectedValue = "rgba(0, 0, 0, 1)"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textHaloColorString = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textHaloColorString)
      pointAnnotationManager.textHaloColorString = null
      assertEquals(
        null,
        pointAnnotationManager.textHaloColorString
      )
    }
  }

  @Test
  fun testTextHaloWidth() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textHaloWidth = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textHaloWidth)
      pointAnnotationManager.textHaloWidth = null
      assertEquals(null, pointAnnotationManager.textHaloWidth)
    }
  }

  @Test
  fun testTextOcclusionOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textOcclusionOpacity = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textOcclusionOpacity)
      pointAnnotationManager.textOcclusionOpacity = null
      assertEquals(null, pointAnnotationManager.textOcclusionOpacity)
    }
  }

  @Test
  fun testTextOpacity() {
    rule.runOnUiThread {
      val expectedValue = 1.0
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.textOpacity = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.textOpacity)
      pointAnnotationManager.textOpacity = null
      assertEquals(null, pointAnnotationManager.textOpacity)
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
  fun testSlot() {
    rule.runOnUiThread {
      val expectedValue = "abc"
      val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
      pointAnnotationManager.slot = expectedValue
      assertEquals(expectedValue, pointAnnotationManager.slot)
      pointAnnotationManager.slot = null
      assertEquals(StyleManager.getStyleLayerPropertyDefaultValue("symbol", "slot").silentUnwrap(), pointAnnotationManager.slot)
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
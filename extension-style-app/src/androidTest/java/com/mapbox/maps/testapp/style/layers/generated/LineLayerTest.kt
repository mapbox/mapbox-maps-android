// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Basic smoke tests for LineLayer
 */
@OptIn(MapboxExperimental::class)
@RunWith(AndroidJUnit4::class)
class LineLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = lineLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = lineLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = lineLayer("id", "source") {
      maxZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.maxZoom)
  }

  @Test
  @UiThreadTest
  fun filterTest() {
    val expression = eq {
      get {
        literal("undefined")
      }
      literal(1.0)
    }
    // Set filter property.
    val layer = lineLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun lineCapTest() {
    val layer = lineLayer("id", "source") {
      lineCap(LineCap.BUTT)
    }
    setupLayer(layer)
    assertEquals(LineCap.BUTT, layer.lineCap)
  }

  @Test
  @UiThreadTest
  fun lineCapAsExpressionTest() {
    val expression = literal("butt")
    val layer = lineLayer("id", "source") {
      lineCap(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineCapAsExpression.toString())
    assertEquals(LineCap.BUTT, layer.lineCap!!)
  }

  @Test
  @UiThreadTest
  fun lineCrossSlopeTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineCrossSlope(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineCrossSlope!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineCrossSlopeAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      lineCrossSlope(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.lineCrossSlopeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineCrossSlope!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineElevationReferenceTest() {
    val layer = lineLayer("id", "source") {
      lineElevationReference(LineElevationReference.NONE)
    }
    setupLayer(layer)
    assertEquals(LineElevationReference.NONE, layer.lineElevationReference)
  }

  @Test
  @UiThreadTest
  fun lineElevationReferenceAsExpressionTest() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {
      lineElevationReference(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineElevationReferenceAsExpression.toString())
    assertEquals(LineElevationReference.NONE, layer.lineElevationReference!!)
  }

  @Test
  @UiThreadTest
  fun lineJoinTest() {
    val layer = lineLayer("id", "source") {
      lineJoin(LineJoin.BEVEL)
    }
    setupLayer(layer)
    assertEquals(LineJoin.BEVEL, layer.lineJoin)
  }

  @Test
  @UiThreadTest
  fun lineJoinAsExpressionTest() {
    val expression = toString {
      get {
        literal("enum")
      }
    }
    val layer = lineLayer("id", "source") {
      lineJoin(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineJoinAsExpression.toString())
    assertEquals(null, layer.lineJoin)
  }

  @Test
  @UiThreadTest
  fun lineMiterLimitTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineMiterLimit(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineMiterLimit!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineMiterLimitAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      lineMiterLimit(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.lineMiterLimitAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineMiterLimit!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineRoundLimitTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineRoundLimit(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineRoundLimit!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineRoundLimitAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      lineRoundLimit(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.lineRoundLimitAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineRoundLimit!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineSortKeyTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineSortKey(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineSortKey!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineSortKeyAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineSortKey(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineSortKeyAsExpression.toString())
    assertEquals(null, layer.lineSortKey)
  }

  @Test
  @UiThreadTest
  fun lineWidthUnitTest() {
    val layer = lineLayer("id", "source") {
      lineWidthUnit(LineWidthUnit.PIXELS)
    }
    setupLayer(layer)
    assertEquals(LineWidthUnit.PIXELS, layer.lineWidthUnit)
  }

  @Test
  @UiThreadTest
  fun lineWidthUnitAsExpressionTest() {
    val expression = literal("pixels")
    val layer = lineLayer("id", "source") {
      lineWidthUnit(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineWidthUnitAsExpression.toString())
    assertEquals(LineWidthUnit.PIXELS, layer.lineWidthUnit!!)
  }

  @Test
  @UiThreadTest
  fun lineZOffsetTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineZOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineZOffset!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineZOffsetAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineZOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineZOffsetAsExpression.toString())
    assertEquals(null, layer.lineZOffset)
  }

  @Test
  @UiThreadTest
  fun lineBlurTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineBlur(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineBlur!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineBlurAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineBlur(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineBlurAsExpression.toString())
    assertEquals(null, layer.lineBlur)
  }

  @Test
  @UiThreadTest
  fun lineBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineBlurTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineBlurTransition)
  }

  @Test
  @UiThreadTest
  fun lineBlurTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineBlurTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineBlurTransition)
  }

  @Test
  @UiThreadTest
  fun lineBorderColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = lineLayer("id", "source") {
      lineBorderColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineBorderColor?.toString())
  }

  @Test
  @UiThreadTest
  fun lineBorderColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = lineLayer("id", "source") {
      lineBorderColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineBorderColorAsExpression.toString())
    assertEquals(null, layer.lineBorderColor)
  }

  @Test
  @UiThreadTest
  fun lineBorderColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {
      lineBorderColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.lineBorderColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineBorderColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = lineLayer("id", "source") {
      lineBorderColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.lineBorderColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineBorderColorAsColorIntTest() {
    val layer = lineLayer("id", "source") {
      lineBorderColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.lineBorderColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun lineBorderColorUseTheme() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineBorderColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.lineBorderColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun lineBorderColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineBorderColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineBorderColorTransition)
  }

  @Test
  @UiThreadTest
  fun lineBorderColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineBorderColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineBorderColorTransition)
  }

  @Test
  @UiThreadTest
  fun lineBorderWidthTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineBorderWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineBorderWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineBorderWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineBorderWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineBorderWidthAsExpression.toString())
    assertEquals(null, layer.lineBorderWidth)
  }

  @Test
  @UiThreadTest
  fun lineBorderWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineBorderWidthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineBorderWidthTransition)
  }

  @Test
  @UiThreadTest
  fun lineBorderWidthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineBorderWidthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineBorderWidthTransition)
  }

  @Test
  @UiThreadTest
  fun lineColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = lineLayer("id", "source") {
      lineColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineColor?.toString())
  }

  @Test
  @UiThreadTest
  fun lineColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = lineLayer("id", "source") {
      lineColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineColorAsExpression.toString())
    assertEquals(null, layer.lineColor)
  }

  @Test
  @UiThreadTest
  fun lineColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {
      lineColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.lineColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = lineLayer("id", "source") {
      lineColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.lineColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineColorAsColorIntTest() {
    val layer = lineLayer("id", "source") {
      lineColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.lineColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun lineColorUseTheme() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.lineColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun lineColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineColorTransition)
  }

  @Test
  @UiThreadTest
  fun lineColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineColorTransition)
  }

  @Test
  @UiThreadTest
  fun lineDasharrayTest() {
    val testValue = listOf(1.0, 2.0)
    val layer = lineLayer("id", "source") {
      lineDasharray(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineDasharray?.toString())
  }

  @Test
  @UiThreadTest
  fun lineDasharrayAsExpressionTest() {
    val expression = literal(listOf(1.0, 2.0))
    val layer = lineLayer("id", "source") {
      lineDasharray(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineDasharrayAsExpression.toString())
    assertEquals(listOf(1.0, 2.0), layer.lineDasharray!!)
  }

  @Test
  @UiThreadTest
  fun lineDepthOcclusionFactorTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineDepthOcclusionFactor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineDepthOcclusionFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineDepthOcclusionFactorAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      lineDepthOcclusionFactor(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.lineDepthOcclusionFactorAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineDepthOcclusionFactor!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineDepthOcclusionFactorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineDepthOcclusionFactorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineDepthOcclusionFactorTransition)
  }

  @Test
  @UiThreadTest
  fun lineDepthOcclusionFactorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineDepthOcclusionFactorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineDepthOcclusionFactorTransition)
  }

  @Test
  @UiThreadTest
  fun lineEmissiveStrengthTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineEmissiveStrength(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineEmissiveStrengthAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      lineEmissiveStrength(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.lineEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineEmissiveStrengthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun lineEmissiveStrengthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineEmissiveStrengthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun lineGapWidthTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineGapWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineGapWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineGapWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineGapWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineGapWidthAsExpression.toString())
    assertEquals(null, layer.lineGapWidth)
  }

  @Test
  @UiThreadTest
  fun lineGapWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineGapWidthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineGapWidthTransition)
  }

  @Test
  @UiThreadTest
  fun lineGapWidthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineGapWidthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineGapWidthTransition)
  }

  @Test
  @UiThreadTest
  fun lineGradientTest() {
    val testValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    val layer = lineLayer("id", "source") {
      lineGradient(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineGradient?.toString())
  }

  @Test
  @UiThreadTest
  fun lineGradientUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {
      lineGradientUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.lineGradientUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineGradientUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = lineLayer("id", "source") {
      lineGradientUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.lineGradientUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineGradientUseTheme() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineGradientUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.lineGradientUseTheme)
  }

  @Test
  @UiThreadTest
  fun lineOcclusionOpacityTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineOcclusionOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineOcclusionOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineOcclusionOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      lineOcclusionOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.lineOcclusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.lineOcclusionOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineOcclusionOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineOcclusionOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineOcclusionOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun lineOcclusionOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineOcclusionOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineOcclusionOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun lineOffsetTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineOffset!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineOffsetAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineOffsetAsExpression.toString())
    assertEquals(null, layer.lineOffset)
  }

  @Test
  @UiThreadTest
  fun lineOffsetTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineOffsetTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineOffsetTransition)
  }

  @Test
  @UiThreadTest
  fun lineOffsetTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineOffsetTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineOffsetTransition)
  }

  @Test
  @UiThreadTest
  fun lineOpacityTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineOpacityAsExpression.toString())
    assertEquals(null, layer.lineOpacity)
  }

  @Test
  @UiThreadTest
  fun lineOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun lineOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun linePatternTest() {
    val testValue = "abc"
    val layer = lineLayer("id", "source") {
      linePattern(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.linePattern?.toString())
  }

  @Test
  @UiThreadTest
  fun linePatternAsExpressionTest() {
    val expression = image {
      string {
        get {
          literal("resolvedImage")
        }
      }
    }
    val layer = lineLayer("id", "source") {
      linePattern(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.linePatternAsExpression.toString())
    assertEquals(null, layer.linePattern)
  }

  @Test
  @UiThreadTest
  fun linePatternCrossFadeTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      linePatternCrossFade(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.linePatternCrossFade!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun linePatternCrossFadeAsExpressionTest() {
    val expression = literal(1.0)
    val layer = lineLayer("id", "source") {
      linePatternCrossFade(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.linePatternCrossFadeAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.linePatternCrossFade!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = lineLayer("id", "source") {
      lineTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun lineTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = lineLayer("id", "source") {
      lineTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.lineTranslate!!)
  }

  @Test
  @UiThreadTest
  fun lineTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun lineTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun lineTranslateAnchorTest() {
    val layer = lineLayer("id", "source") {
      lineTranslateAnchor(LineTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(LineTranslateAnchor.MAP, layer.lineTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun lineTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = lineLayer("id", "source") {
      lineTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineTranslateAnchorAsExpression.toString())
    assertEquals(LineTranslateAnchor.MAP, layer.lineTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun lineTrimColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = lineLayer("id", "source") {
      lineTrimColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineTrimColor?.toString())
  }

  @Test
  @UiThreadTest
  fun lineTrimColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = lineLayer("id", "source") {
      lineTrimColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineTrimColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.lineTrimColor)
    assertEquals(Color.BLACK, layer.lineTrimColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun lineTrimColorUseThemeAsExpressionTest() {
    val expression = literal("none")
    val layer = lineLayer("id", "source") {
      lineTrimColorUseTheme(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.lineTrimColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineTrimColorUseThemeStringAsExpressionTest() {
    val testValue = "none"
    val layer = lineLayer("id", "source") {
      lineTrimColorUseTheme(testValue)
    }
    setupLayer(layer)
    assertEquals(literal(testValue).toString(), layer.lineTrimColorUseThemeAsExpression.toString())
  }

  @Test
  @UiThreadTest
  fun lineTrimColorAsColorIntTest() {
    val layer = lineLayer("id", "source") {
      lineTrimColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.lineTrimColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun lineTrimColorUseTheme() {
    val theme = "none"
    val layer = lineLayer("id", "source") {
      lineTrimColorUseTheme(theme)
    }
    setupLayer(layer)
    assertEquals(theme, layer.lineTrimColorUseTheme)
  }

  @Test
  @UiThreadTest
  fun lineTrimColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineTrimColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineTrimColorTransition)
  }

  @Test
  @UiThreadTest
  fun lineTrimColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineTrimColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineTrimColorTransition)
  }

  @Test
  @UiThreadTest
  fun lineTrimFadeRangeTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = lineLayer("id", "source") {
      lineTrimFadeRange(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineTrimFadeRange?.toString())
  }

  @Test
  @UiThreadTest
  fun lineTrimFadeRangeAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = lineLayer("id", "source") {
      lineTrimFadeRange(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineTrimFadeRangeAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.lineTrimFadeRange!!)
  }

  @Test
  @UiThreadTest
  fun lineTrimOffsetTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = lineLayer("id", "source") {
      lineTrimOffset(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.lineTrimOffset?.toString())
  }

  @Test
  @UiThreadTest
  fun lineTrimOffsetAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = lineLayer("id", "source") {
      lineTrimOffset(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineTrimOffsetAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.lineTrimOffset!!)
  }

  @Test
  @UiThreadTest
  fun lineWidthTest() {
    val testValue = 1.0
    val layer = lineLayer("id", "source") {
      lineWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.lineWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun lineWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = lineLayer("id", "source") {
      lineWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.lineWidthAsExpression.toString())
    assertEquals(null, layer.lineWidth)
  }

  @Test
  @UiThreadTest
  fun lineWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineWidthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineWidthTransition)
  }

  @Test
  @UiThreadTest
  fun lineWidthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = lineLayer("id", "source") {
      lineWidthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.lineWidthTransition)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = lineLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = lineLayer("id", "source") {
      visibility(
        concat {
          literal("no")
          literal("ne")
        }
      )
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", LineLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", LineLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", LineLayer.defaultMaxZoom)
    assertNotNull("defaultLineCap should not be null", LineLayer.defaultLineCap)
    assertNotNull("defaultLineCapAsExpression should not be null", LineLayer.defaultLineCapAsExpression)
    assertNotNull("defaultLineCrossSlope should not be null", LineLayer.defaultLineCrossSlope)
    assertNotNull("defaultLineCrossSlopeAsExpression should not be null", LineLayer.defaultLineCrossSlopeAsExpression)
    assertNotNull("defaultLineElevationReference should not be null", LineLayer.defaultLineElevationReference)
    assertNotNull("defaultLineElevationReferenceAsExpression should not be null", LineLayer.defaultLineElevationReferenceAsExpression)
    assertNotNull("defaultLineJoin should not be null", LineLayer.defaultLineJoin)
    assertNotNull("defaultLineJoinAsExpression should not be null", LineLayer.defaultLineJoinAsExpression)
    assertNotNull("defaultLineMiterLimit should not be null", LineLayer.defaultLineMiterLimit)
    assertNotNull("defaultLineMiterLimitAsExpression should not be null", LineLayer.defaultLineMiterLimitAsExpression)
    assertNotNull("defaultLineRoundLimit should not be null", LineLayer.defaultLineRoundLimit)
    assertNotNull("defaultLineRoundLimitAsExpression should not be null", LineLayer.defaultLineRoundLimitAsExpression)
    assertNotNull("defaultLineSortKey should not be null", LineLayer.defaultLineSortKey)
    assertNotNull("defaultLineSortKeyAsExpression should not be null", LineLayer.defaultLineSortKeyAsExpression)
    assertNotNull("defaultLineWidthUnit should not be null", LineLayer.defaultLineWidthUnit)
    assertNotNull("defaultLineWidthUnitAsExpression should not be null", LineLayer.defaultLineWidthUnitAsExpression)
    assertNotNull("defaultLineZOffset should not be null", LineLayer.defaultLineZOffset)
    assertNotNull("defaultLineZOffsetAsExpression should not be null", LineLayer.defaultLineZOffsetAsExpression)
    assertNotNull("defaultLineBlur should not be null", LineLayer.defaultLineBlur)
    assertNotNull("defaultLineBlurAsExpression should not be null", LineLayer.defaultLineBlurAsExpression)
    assertNotNull("defaultLineBlurTransition should not be null", LineLayer.defaultLineBlurTransition)
    assertNotNull("defaultLineBorderColor should not be null", LineLayer.defaultLineBorderColor)
    assertNotNull("defaultLineBorderColorAsExpression should not be null", LineLayer.defaultLineBorderColorAsExpression)
    assertNotNull("defaultLineBorderColorAsColorInt should not be null", LineLayer.defaultLineBorderColorAsColorInt)
    assertNotNull("defaultLineBorderColorUseTheme should not be null", LineLayer.defaultLineBorderColorUseTheme)
    assertNotNull("defaultLineBorderColorUseThemeAsExpression should not be null", LineLayer.defaultLineBorderColorUseThemeAsExpression)
    assertNotNull("defaultLineBorderColorTransition should not be null", LineLayer.defaultLineBorderColorTransition)
    assertNotNull("defaultLineBorderWidth should not be null", LineLayer.defaultLineBorderWidth)
    assertNotNull("defaultLineBorderWidthAsExpression should not be null", LineLayer.defaultLineBorderWidthAsExpression)
    assertNotNull("defaultLineBorderWidthTransition should not be null", LineLayer.defaultLineBorderWidthTransition)
    assertNotNull("defaultLineColor should not be null", LineLayer.defaultLineColor)
    assertNotNull("defaultLineColorAsExpression should not be null", LineLayer.defaultLineColorAsExpression)
    assertNotNull("defaultLineColorAsColorInt should not be null", LineLayer.defaultLineColorAsColorInt)
    assertNotNull("defaultLineColorUseTheme should not be null", LineLayer.defaultLineColorUseTheme)
    assertNotNull("defaultLineColorUseThemeAsExpression should not be null", LineLayer.defaultLineColorUseThemeAsExpression)
    assertNotNull("defaultLineColorTransition should not be null", LineLayer.defaultLineColorTransition)
    assertNotNull("defaultLineDasharray should not be null", LineLayer.defaultLineDasharray)
    assertNotNull("defaultLineDasharrayAsExpression should not be null", LineLayer.defaultLineDasharrayAsExpression)
    assertNotNull("defaultLineDepthOcclusionFactor should not be null", LineLayer.defaultLineDepthOcclusionFactor)
    assertNotNull("defaultLineDepthOcclusionFactorAsExpression should not be null", LineLayer.defaultLineDepthOcclusionFactorAsExpression)
    assertNotNull("defaultLineDepthOcclusionFactorTransition should not be null", LineLayer.defaultLineDepthOcclusionFactorTransition)
    assertNotNull("defaultLineEmissiveStrength should not be null", LineLayer.defaultLineEmissiveStrength)
    assertNotNull("defaultLineEmissiveStrengthAsExpression should not be null", LineLayer.defaultLineEmissiveStrengthAsExpression)
    assertNotNull("defaultLineEmissiveStrengthTransition should not be null", LineLayer.defaultLineEmissiveStrengthTransition)
    assertNotNull("defaultLineGapWidth should not be null", LineLayer.defaultLineGapWidth)
    assertNotNull("defaultLineGapWidthAsExpression should not be null", LineLayer.defaultLineGapWidthAsExpression)
    assertNotNull("defaultLineGapWidthTransition should not be null", LineLayer.defaultLineGapWidthTransition)
    assertNotNull("defaultLineGradientUseTheme should not be null", LineLayer.defaultLineGradientUseTheme)
    assertNotNull("defaultLineGradientUseThemeAsExpression should not be null", LineLayer.defaultLineGradientUseThemeAsExpression)
    assertNotNull("defaultLineOcclusionOpacity should not be null", LineLayer.defaultLineOcclusionOpacity)
    assertNotNull("defaultLineOcclusionOpacityAsExpression should not be null", LineLayer.defaultLineOcclusionOpacityAsExpression)
    assertNotNull("defaultLineOcclusionOpacityTransition should not be null", LineLayer.defaultLineOcclusionOpacityTransition)
    assertNotNull("defaultLineOffset should not be null", LineLayer.defaultLineOffset)
    assertNotNull("defaultLineOffsetAsExpression should not be null", LineLayer.defaultLineOffsetAsExpression)
    assertNotNull("defaultLineOffsetTransition should not be null", LineLayer.defaultLineOffsetTransition)
    assertNotNull("defaultLineOpacity should not be null", LineLayer.defaultLineOpacity)
    assertNotNull("defaultLineOpacityAsExpression should not be null", LineLayer.defaultLineOpacityAsExpression)
    assertNotNull("defaultLineOpacityTransition should not be null", LineLayer.defaultLineOpacityTransition)
    assertNotNull("defaultLinePattern should not be null", LineLayer.defaultLinePattern)
    assertNotNull("defaultLinePatternAsExpression should not be null", LineLayer.defaultLinePatternAsExpression)
    assertNotNull("defaultLinePatternCrossFade should not be null", LineLayer.defaultLinePatternCrossFade)
    assertNotNull("defaultLinePatternCrossFadeAsExpression should not be null", LineLayer.defaultLinePatternCrossFadeAsExpression)
    assertNotNull("defaultLineTranslate should not be null", LineLayer.defaultLineTranslate)
    assertNotNull("defaultLineTranslateAsExpression should not be null", LineLayer.defaultLineTranslateAsExpression)
    assertNotNull("defaultLineTranslateTransition should not be null", LineLayer.defaultLineTranslateTransition)
    assertNotNull("defaultLineTranslateAnchor should not be null", LineLayer.defaultLineTranslateAnchor)
    assertNotNull("defaultLineTranslateAnchorAsExpression should not be null", LineLayer.defaultLineTranslateAnchorAsExpression)
    assertNotNull("defaultLineTrimColor should not be null", LineLayer.defaultLineTrimColor)
    assertNotNull("defaultLineTrimColorAsExpression should not be null", LineLayer.defaultLineTrimColorAsExpression)
    assertNotNull("defaultLineTrimColorAsColorInt should not be null", LineLayer.defaultLineTrimColorAsColorInt)
    assertNotNull("defaultLineTrimColorUseTheme should not be null", LineLayer.defaultLineTrimColorUseTheme)
    assertNotNull("defaultLineTrimColorUseThemeAsExpression should not be null", LineLayer.defaultLineTrimColorUseThemeAsExpression)
    assertNotNull("defaultLineTrimColorTransition should not be null", LineLayer.defaultLineTrimColorTransition)
    assertNotNull("defaultLineTrimFadeRange should not be null", LineLayer.defaultLineTrimFadeRange)
    assertNotNull("defaultLineTrimFadeRangeAsExpression should not be null", LineLayer.defaultLineTrimFadeRangeAsExpression)
    assertNotNull("defaultLineTrimOffset should not be null", LineLayer.defaultLineTrimOffset)
    assertNotNull("defaultLineTrimOffsetAsExpression should not be null", LineLayer.defaultLineTrimOffsetAsExpression)
    assertNotNull("defaultLineWidth should not be null", LineLayer.defaultLineWidth)
    assertNotNull("defaultLineWidthAsExpression should not be null", LineLayer.defaultLineWidthAsExpression)
    assertNotNull("defaultLineWidthTransition should not be null", LineLayer.defaultLineWidthTransition)
  }

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val filterTestValue = eq {
      get {
        literal("undefined")
      }
      literal(1.0)
    }
    val lineCapTestValue = LineCap.BUTT
    val lineCrossSlopeTestValue = 1.0
    val lineElevationReferenceTestValue = LineElevationReference.NONE
    val lineJoinTestValue = LineJoin.BEVEL
    val lineMiterLimitTestValue = 1.0
    val lineRoundLimitTestValue = 1.0
    val lineSortKeyTestValue = 1.0
    val lineWidthUnitTestValue = LineWidthUnit.PIXELS
    val lineZOffsetTestValue = 1.0
    val lineBlurTestValue = 1.0
    val lineBorderColorTestValue = "rgba(0, 0, 0, 1)"
    val lineBorderColorUseThemeTestValue = "default"
    val lineBorderWidthTestValue = 1.0
    val lineColorTestValue = "rgba(0, 0, 0, 1)"
    val lineColorUseThemeTestValue = "default"
    val lineDasharrayTestValue = listOf(1.0, 2.0)
    val lineDepthOcclusionFactorTestValue = 1.0
    val lineEmissiveStrengthTestValue = 1.0
    val lineGapWidthTestValue = 1.0
    val lineGradientTestValue = interpolate {
      linear()
      heatmapDensity()
      stop {
        literal(0.0)
        rgba {
          literal(0.0)
          literal(0.0)
          literal(0.0)
          literal(0.0)
        }
      }
      stop {
        literal(1.0)
        rgba {
          literal(0.0)
          literal(255.0)
          literal(0.0)
          literal(1.0)
        }
      }
    }
    val lineGradientUseThemeTestValue = "default"
    val lineOcclusionOpacityTestValue = 1.0
    val lineOffsetTestValue = 1.0
    val lineOpacityTestValue = 1.0
    val linePatternTestValue = "abc"
    val linePatternCrossFadeTestValue = 1.0
    val lineTranslateTestValue = listOf(0.0, 1.0)
    val lineTranslateAnchorTestValue = LineTranslateAnchor.MAP
    val lineTrimColorTestValue = "rgba(0, 0, 0, 1)"
    val lineTrimColorUseThemeTestValue = "default"
    val lineTrimFadeRangeTestValue = listOf(0.0, 1.0)
    val lineTrimOffsetTestValue = listOf(0.0, 1.0)
    val lineWidthTestValue = 1.0

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = lineLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      lineCap(lineCapTestValue)
      lineCrossSlope(lineCrossSlopeTestValue)
      lineElevationReference(lineElevationReferenceTestValue)
      lineJoin(lineJoinTestValue)
      lineMiterLimit(lineMiterLimitTestValue)
      lineRoundLimit(lineRoundLimitTestValue)
      lineSortKey(lineSortKeyTestValue)
      lineWidthUnit(lineWidthUnitTestValue)
      lineZOffset(lineZOffsetTestValue)
      lineBlur(lineBlurTestValue)
      lineBorderColor(lineBorderColorTestValue)
      lineBorderColorUseTheme(lineBorderColorUseThemeTestValue)
      lineBorderWidth(lineBorderWidthTestValue)
      lineColor(lineColorTestValue)
      lineColorUseTheme(lineColorUseThemeTestValue)
      lineDasharray(lineDasharrayTestValue)
      lineDepthOcclusionFactor(lineDepthOcclusionFactorTestValue)
      lineEmissiveStrength(lineEmissiveStrengthTestValue)
      lineGapWidth(lineGapWidthTestValue)
      lineGradient(lineGradientTestValue)
      lineGradientUseTheme(lineGradientUseThemeTestValue)
      lineOcclusionOpacity(lineOcclusionOpacityTestValue)
      lineOffset(lineOffsetTestValue)
      lineOpacity(lineOpacityTestValue)
      linePattern(linePatternTestValue)
      linePatternCrossFade(linePatternCrossFadeTestValue)
      lineTranslate(lineTranslateTestValue)
      lineTranslateAnchor(lineTranslateAnchorTestValue)
      lineTrimColor(lineTrimColorTestValue)
      lineTrimColorUseTheme(lineTrimColorUseThemeTestValue)
      lineTrimFadeRange(lineTrimFadeRangeTestValue)
      lineTrimOffset(lineTrimOffsetTestValue)
      lineWidth(lineWidthTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as LineLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(lineCapTestValue, cachedLayer.lineCap)
    assertEquals(lineCrossSlopeTestValue, cachedLayer.lineCrossSlope)
    assertEquals(lineElevationReferenceTestValue, cachedLayer.lineElevationReference)
    assertEquals(lineJoinTestValue, cachedLayer.lineJoin)
    assertEquals(lineMiterLimitTestValue, cachedLayer.lineMiterLimit)
    assertEquals(lineRoundLimitTestValue, cachedLayer.lineRoundLimit)
    assertEquals(lineSortKeyTestValue, cachedLayer.lineSortKey)
    assertEquals(lineWidthUnitTestValue, cachedLayer.lineWidthUnit)
    assertEquals(lineZOffsetTestValue, cachedLayer.lineZOffset)
    assertEquals(lineBlurTestValue, cachedLayer.lineBlur)
    assertEquals(lineBorderColorTestValue, cachedLayer.lineBorderColor)
    assertEquals(lineBorderColorUseThemeTestValue, cachedLayer.lineBorderColorUseTheme)
    assertEquals(lineBorderWidthTestValue, cachedLayer.lineBorderWidth)
    assertEquals(lineColorTestValue, cachedLayer.lineColor)
    assertEquals(lineColorUseThemeTestValue, cachedLayer.lineColorUseTheme)
    assertEquals(lineDasharrayTestValue, cachedLayer.lineDasharray)
    assertEquals(lineDepthOcclusionFactorTestValue, cachedLayer.lineDepthOcclusionFactor)
    assertEquals(lineEmissiveStrengthTestValue, cachedLayer.lineEmissiveStrength)
    assertEquals(lineGapWidthTestValue, cachedLayer.lineGapWidth)
    assertEquals(lineGradientTestValue, cachedLayer.lineGradient)
    assertEquals(lineGradientUseThemeTestValue, cachedLayer.lineGradientUseTheme)
    assertEquals(lineOcclusionOpacityTestValue, cachedLayer.lineOcclusionOpacity)
    assertEquals(lineOffsetTestValue, cachedLayer.lineOffset)
    assertEquals(lineOpacityTestValue, cachedLayer.lineOpacity)
    assertEquals(linePatternTestValue, cachedLayer.linePattern)
    assertEquals(linePatternCrossFadeTestValue, cachedLayer.linePatternCrossFade)
    assertEquals(lineTranslateTestValue, cachedLayer.lineTranslate)
    assertEquals(lineTranslateAnchorTestValue, cachedLayer.lineTranslateAnchor)
    assertEquals(lineTrimColorTestValue, cachedLayer.lineTrimColor)
    assertEquals(lineTrimColorUseThemeTestValue, cachedLayer.lineTrimColorUseTheme)
    assertEquals(lineTrimFadeRangeTestValue, cachedLayer.lineTrimFadeRange)
    assertEquals(lineTrimOffsetTestValue, cachedLayer.lineTrimOffset)
    assertEquals(lineWidthTestValue, cachedLayer.lineWidth)
  }
}

// End of generated file.
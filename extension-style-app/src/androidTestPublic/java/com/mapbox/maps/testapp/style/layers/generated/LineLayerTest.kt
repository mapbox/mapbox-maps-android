// This file is generated.

package com.mapbox.maps.testapp.style.layers.generated

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
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
  fun lineColorAsColorIntTest() {
    val layer = lineLayer("id", "source") {
      lineColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.lineColorAsColorInt)
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

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", LineLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", LineLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", LineLayer.defaultMaxZoom)
    assertNotNull("defaultLineCap should not be null", LineLayer.defaultLineCap)
    assertNotNull("defaultLineCapAsExpression should not be null", LineLayer.defaultLineCapAsExpression)
    assertNotNull("defaultLineJoin should not be null", LineLayer.defaultLineJoin)
    assertNotNull("defaultLineJoinAsExpression should not be null", LineLayer.defaultLineJoinAsExpression)
    assertNotNull("defaultLineMiterLimit should not be null", LineLayer.defaultLineMiterLimit)
    assertNotNull("defaultLineMiterLimitAsExpression should not be null", LineLayer.defaultLineMiterLimitAsExpression)
    assertNotNull("defaultLineRoundLimit should not be null", LineLayer.defaultLineRoundLimit)
    assertNotNull("defaultLineRoundLimitAsExpression should not be null", LineLayer.defaultLineRoundLimitAsExpression)
    assertNotNull("defaultLineSortKey should not be null", LineLayer.defaultLineSortKey)
    assertNotNull("defaultLineSortKeyAsExpression should not be null", LineLayer.defaultLineSortKeyAsExpression)
    assertNotNull("defaultLineBlur should not be null", LineLayer.defaultLineBlur)
    assertNotNull("defaultLineBlurAsExpression should not be null", LineLayer.defaultLineBlurAsExpression)
    assertNotNull("defaultLineBlurTransition should not be null", LineLayer.defaultLineBlurTransition)
    assertNotNull("defaultLineColor should not be null", LineLayer.defaultLineColor)
    assertNotNull("defaultLineColorAsExpression should not be null", LineLayer.defaultLineColorAsExpression)
    assertNotNull("defaultLineColorAsColorInt should not be null", LineLayer.defaultLineColorAsColorInt)
    assertNotNull("defaultLineColorTransition should not be null", LineLayer.defaultLineColorTransition)
    assertNotNull("defaultLineDasharray should not be null", LineLayer.defaultLineDasharray)
    assertNotNull("defaultLineDasharrayAsExpression should not be null", LineLayer.defaultLineDasharrayAsExpression)
    assertNotNull("defaultLineDepthOcclusionFactor should not be null", LineLayer.defaultLineDepthOcclusionFactor)
    assertNotNull("defaultLineDepthOcclusionFactorAsExpression should not be null", LineLayer.defaultLineDepthOcclusionFactorAsExpression)
    assertNotNull("defaultLineDepthOcclusionFactorTransition should not be null", LineLayer.defaultLineDepthOcclusionFactorTransition)
    assertNotNull("defaultLineGapWidth should not be null", LineLayer.defaultLineGapWidth)
    assertNotNull("defaultLineGapWidthAsExpression should not be null", LineLayer.defaultLineGapWidthAsExpression)
    assertNotNull("defaultLineGapWidthTransition should not be null", LineLayer.defaultLineGapWidthTransition)
    assertNotNull("defaultLineOffset should not be null", LineLayer.defaultLineOffset)
    assertNotNull("defaultLineOffsetAsExpression should not be null", LineLayer.defaultLineOffsetAsExpression)
    assertNotNull("defaultLineOffsetTransition should not be null", LineLayer.defaultLineOffsetTransition)
    assertNotNull("defaultLineOpacity should not be null", LineLayer.defaultLineOpacity)
    assertNotNull("defaultLineOpacityAsExpression should not be null", LineLayer.defaultLineOpacityAsExpression)
    assertNotNull("defaultLineOpacityTransition should not be null", LineLayer.defaultLineOpacityTransition)
    assertNotNull("defaultLinePattern should not be null", LineLayer.defaultLinePattern)
    assertNotNull("defaultLinePatternAsExpression should not be null", LineLayer.defaultLinePatternAsExpression)
    assertNotNull("defaultLineTranslate should not be null", LineLayer.defaultLineTranslate)
    assertNotNull("defaultLineTranslateAsExpression should not be null", LineLayer.defaultLineTranslateAsExpression)
    assertNotNull("defaultLineTranslateTransition should not be null", LineLayer.defaultLineTranslateTransition)
    assertNotNull("defaultLineTranslateAnchor should not be null", LineLayer.defaultLineTranslateAnchor)
    assertNotNull("defaultLineTranslateAnchorAsExpression should not be null", LineLayer.defaultLineTranslateAnchorAsExpression)
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
    val lineJoinTestValue = LineJoin.BEVEL
    val lineMiterLimitTestValue = 1.0
    val lineRoundLimitTestValue = 1.0
    val lineSortKeyTestValue = 1.0
    val lineBlurTestValue = 1.0
    val lineColorTestValue = "rgba(0, 0, 0, 1)"
    val lineDasharrayTestValue = listOf(1.0, 2.0)
    val lineDepthOcclusionFactorTestValue = 1.0
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
    val lineOffsetTestValue = 1.0
    val lineOpacityTestValue = 1.0
    val linePatternTestValue = "abc"
    val lineTranslateTestValue = listOf(0.0, 1.0)
    val lineTranslateAnchorTestValue = LineTranslateAnchor.MAP
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
      lineJoin(lineJoinTestValue)
      lineMiterLimit(lineMiterLimitTestValue)
      lineRoundLimit(lineRoundLimitTestValue)
      lineSortKey(lineSortKeyTestValue)
      lineBlur(lineBlurTestValue)
      lineColor(lineColorTestValue)
      lineDasharray(lineDasharrayTestValue)
      lineDepthOcclusionFactor(lineDepthOcclusionFactorTestValue)
      lineGapWidth(lineGapWidthTestValue)
      lineGradient(lineGradientTestValue)
      lineOffset(lineOffsetTestValue)
      lineOpacity(lineOpacityTestValue)
      linePattern(linePatternTestValue)
      lineTranslate(lineTranslateTestValue)
      lineTranslateAnchor(lineTranslateAnchorTestValue)
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
    assertEquals(lineJoinTestValue, cachedLayer.lineJoin)
    assertEquals(lineMiterLimitTestValue, cachedLayer.lineMiterLimit)
    assertEquals(lineRoundLimitTestValue, cachedLayer.lineRoundLimit)
    assertEquals(lineSortKeyTestValue, cachedLayer.lineSortKey)
    assertEquals(lineBlurTestValue, cachedLayer.lineBlur)
    assertEquals(lineColorTestValue, cachedLayer.lineColor)
    assertEquals(lineDasharrayTestValue, cachedLayer.lineDasharray)
    assertEquals(lineDepthOcclusionFactorTestValue, cachedLayer.lineDepthOcclusionFactor)
    assertEquals(lineGapWidthTestValue, cachedLayer.lineGapWidth)
    assertEquals(lineGradientTestValue, cachedLayer.lineGradient)
    assertEquals(lineOffsetTestValue, cachedLayer.lineOffset)
    assertEquals(lineOpacityTestValue, cachedLayer.lineOpacity)
    assertEquals(linePatternTestValue, cachedLayer.linePattern)
    assertEquals(lineTranslateTestValue, cachedLayer.lineTranslate)
    assertEquals(lineTranslateAnchorTestValue, cachedLayer.lineTranslateAnchor)
    assertEquals(lineTrimOffsetTestValue, cachedLayer.lineTrimOffset)
    assertEquals(lineWidthTestValue, cachedLayer.lineWidth)
  }
}

// End of generated file.
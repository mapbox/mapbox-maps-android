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
 * Basic smoke tests for CircleLayer
 */
@RunWith(AndroidJUnit4::class)
class CircleLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = circleLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = circleLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = circleLayer("id", "source") {
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
    val layer = circleLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun circleSortKeyTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleSortKey(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleSortKey!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleSortKeyAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = circleLayer("id", "source") {
      circleSortKey(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleSortKeyAsExpression.toString())
    assertEquals(null, layer.circleSortKey)
  }

  @Test
  @UiThreadTest
  fun circleBlurTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleBlur(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleBlur!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleBlurAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = circleLayer("id", "source") {
      circleBlur(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleBlurAsExpression.toString())
    assertEquals(null, layer.circleBlur)
  }

  @Test
  @UiThreadTest
  fun circleBlurTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleBlurTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleBlurTransition)
  }

  @Test
  @UiThreadTest
  fun circleBlurTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleBlurTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleBlurTransition)
  }

  @Test
  @UiThreadTest
  fun circleColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = circleLayer("id", "source") {
      circleColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.circleColor?.toString())
  }

  @Test
  @UiThreadTest
  fun circleColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = circleLayer("id", "source") {
      circleColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleColorAsExpression.toString())
    assertEquals(null, layer.circleColor)
  }

  @Test
  @UiThreadTest
  fun circleColorAsColorIntTest() {
    val layer = circleLayer("id", "source") {
      circleColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.circleColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun circleColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleColorTransition)
  }

  @Test
  @UiThreadTest
  fun circleColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleColorTransition)
  }

  @Test
  @UiThreadTest
  fun circleEmissiveStrengthTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleEmissiveStrength(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleEmissiveStrengthAsExpressionTest() {
    val expression = literal(1.0)
    val layer = circleLayer("id", "source") {
      circleEmissiveStrength(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.circleEmissiveStrengthAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.circleEmissiveStrength!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleEmissiveStrengthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleEmissiveStrengthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun circleEmissiveStrengthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleEmissiveStrengthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleEmissiveStrengthTransition)
  }

  @Test
  @UiThreadTest
  fun circleOpacityTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = circleLayer("id", "source") {
      circleOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleOpacityAsExpression.toString())
    assertEquals(null, layer.circleOpacity)
  }

  @Test
  @UiThreadTest
  fun circleOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun circleOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun circlePitchAlignmentTest() {
    val layer = circleLayer("id", "source") {
      circlePitchAlignment(CirclePitchAlignment.MAP)
    }
    setupLayer(layer)
    assertEquals(CirclePitchAlignment.MAP, layer.circlePitchAlignment)
  }

  @Test
  @UiThreadTest
  fun circlePitchAlignmentAsExpressionTest() {
    val expression = literal("map")
    val layer = circleLayer("id", "source") {
      circlePitchAlignment(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circlePitchAlignmentAsExpression.toString())
    assertEquals(CirclePitchAlignment.MAP, layer.circlePitchAlignment!!)
  }

  @Test
  @UiThreadTest
  fun circlePitchScaleTest() {
    val layer = circleLayer("id", "source") {
      circlePitchScale(CirclePitchScale.MAP)
    }
    setupLayer(layer)
    assertEquals(CirclePitchScale.MAP, layer.circlePitchScale)
  }

  @Test
  @UiThreadTest
  fun circlePitchScaleAsExpressionTest() {
    val expression = literal("map")
    val layer = circleLayer("id", "source") {
      circlePitchScale(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circlePitchScaleAsExpression.toString())
    assertEquals(CirclePitchScale.MAP, layer.circlePitchScale!!)
  }

  @Test
  @UiThreadTest
  fun circleRadiusTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleRadiusAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = circleLayer("id", "source") {
      circleRadius(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleRadiusAsExpression.toString())
    assertEquals(null, layer.circleRadius)
  }

  @Test
  @UiThreadTest
  fun circleRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun circleRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun circleStrokeColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = circleLayer("id", "source") {
      circleStrokeColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.circleStrokeColor?.toString())
  }

  @Test
  @UiThreadTest
  fun circleStrokeColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = circleLayer("id", "source") {
      circleStrokeColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleStrokeColorAsExpression.toString())
    assertEquals(null, layer.circleStrokeColor)
  }

  @Test
  @UiThreadTest
  fun circleStrokeColorAsColorIntTest() {
    val layer = circleLayer("id", "source") {
      circleStrokeColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.circleStrokeColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun circleStrokeColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleStrokeColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleStrokeColorTransition)
  }

  @Test
  @UiThreadTest
  fun circleStrokeColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleStrokeColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleStrokeColorTransition)
  }

  @Test
  @UiThreadTest
  fun circleStrokeOpacityTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleStrokeOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleStrokeOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleStrokeOpacityAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = circleLayer("id", "source") {
      circleStrokeOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleStrokeOpacityAsExpression.toString())
    assertEquals(null, layer.circleStrokeOpacity)
  }

  @Test
  @UiThreadTest
  fun circleStrokeOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleStrokeOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleStrokeOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun circleStrokeOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleStrokeOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleStrokeOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun circleStrokeWidthTest() {
    val testValue = 1.0
    val layer = circleLayer("id", "source") {
      circleStrokeWidth(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.circleStrokeWidth!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun circleStrokeWidthAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = circleLayer("id", "source") {
      circleStrokeWidth(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleStrokeWidthAsExpression.toString())
    assertEquals(null, layer.circleStrokeWidth)
  }

  @Test
  @UiThreadTest
  fun circleStrokeWidthTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleStrokeWidthTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleStrokeWidthTransition)
  }

  @Test
  @UiThreadTest
  fun circleStrokeWidthTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleStrokeWidthTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleStrokeWidthTransition)
  }

  @Test
  @UiThreadTest
  fun circleTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = circleLayer("id", "source") {
      circleTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.circleTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun circleTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = circleLayer("id", "source") {
      circleTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.circleTranslate!!)
  }

  @Test
  @UiThreadTest
  fun circleTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun circleTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = circleLayer("id", "source") {
      circleTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.circleTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun circleTranslateAnchorTest() {
    val layer = circleLayer("id", "source") {
      circleTranslateAnchor(CircleTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(CircleTranslateAnchor.MAP, layer.circleTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun circleTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = circleLayer("id", "source") {
      circleTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.circleTranslateAnchorAsExpression.toString())
    assertEquals(CircleTranslateAnchor.MAP, layer.circleTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = circleLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  @Test
  @UiThreadTest
  fun visibilityAsExpressionTest() {
    val layer = circleLayer("id", "source") {
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
    assertNotNull("defaultVisibility should not be null", CircleLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", CircleLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", CircleLayer.defaultMaxZoom)
    assertNotNull("defaultCircleSortKey should not be null", CircleLayer.defaultCircleSortKey)
    assertNotNull("defaultCircleSortKeyAsExpression should not be null", CircleLayer.defaultCircleSortKeyAsExpression)
    assertNotNull("defaultCircleBlur should not be null", CircleLayer.defaultCircleBlur)
    assertNotNull("defaultCircleBlurAsExpression should not be null", CircleLayer.defaultCircleBlurAsExpression)
    assertNotNull("defaultCircleBlurTransition should not be null", CircleLayer.defaultCircleBlurTransition)
    assertNotNull("defaultCircleColor should not be null", CircleLayer.defaultCircleColor)
    assertNotNull("defaultCircleColorAsExpression should not be null", CircleLayer.defaultCircleColorAsExpression)
    assertNotNull("defaultCircleColorAsColorInt should not be null", CircleLayer.defaultCircleColorAsColorInt)
    assertNotNull("defaultCircleColorTransition should not be null", CircleLayer.defaultCircleColorTransition)
    assertNotNull("defaultCircleEmissiveStrength should not be null", CircleLayer.defaultCircleEmissiveStrength)
    assertNotNull("defaultCircleEmissiveStrengthAsExpression should not be null", CircleLayer.defaultCircleEmissiveStrengthAsExpression)
    assertNotNull("defaultCircleEmissiveStrengthTransition should not be null", CircleLayer.defaultCircleEmissiveStrengthTransition)
    assertNotNull("defaultCircleOpacity should not be null", CircleLayer.defaultCircleOpacity)
    assertNotNull("defaultCircleOpacityAsExpression should not be null", CircleLayer.defaultCircleOpacityAsExpression)
    assertNotNull("defaultCircleOpacityTransition should not be null", CircleLayer.defaultCircleOpacityTransition)
    assertNotNull("defaultCirclePitchAlignment should not be null", CircleLayer.defaultCirclePitchAlignment)
    assertNotNull("defaultCirclePitchAlignmentAsExpression should not be null", CircleLayer.defaultCirclePitchAlignmentAsExpression)
    assertNotNull("defaultCirclePitchScale should not be null", CircleLayer.defaultCirclePitchScale)
    assertNotNull("defaultCirclePitchScaleAsExpression should not be null", CircleLayer.defaultCirclePitchScaleAsExpression)
    assertNotNull("defaultCircleRadius should not be null", CircleLayer.defaultCircleRadius)
    assertNotNull("defaultCircleRadiusAsExpression should not be null", CircleLayer.defaultCircleRadiusAsExpression)
    assertNotNull("defaultCircleRadiusTransition should not be null", CircleLayer.defaultCircleRadiusTransition)
    assertNotNull("defaultCircleStrokeColor should not be null", CircleLayer.defaultCircleStrokeColor)
    assertNotNull("defaultCircleStrokeColorAsExpression should not be null", CircleLayer.defaultCircleStrokeColorAsExpression)
    assertNotNull("defaultCircleStrokeColorAsColorInt should not be null", CircleLayer.defaultCircleStrokeColorAsColorInt)
    assertNotNull("defaultCircleStrokeColorTransition should not be null", CircleLayer.defaultCircleStrokeColorTransition)
    assertNotNull("defaultCircleStrokeOpacity should not be null", CircleLayer.defaultCircleStrokeOpacity)
    assertNotNull("defaultCircleStrokeOpacityAsExpression should not be null", CircleLayer.defaultCircleStrokeOpacityAsExpression)
    assertNotNull("defaultCircleStrokeOpacityTransition should not be null", CircleLayer.defaultCircleStrokeOpacityTransition)
    assertNotNull("defaultCircleStrokeWidth should not be null", CircleLayer.defaultCircleStrokeWidth)
    assertNotNull("defaultCircleStrokeWidthAsExpression should not be null", CircleLayer.defaultCircleStrokeWidthAsExpression)
    assertNotNull("defaultCircleStrokeWidthTransition should not be null", CircleLayer.defaultCircleStrokeWidthTransition)
    assertNotNull("defaultCircleTranslate should not be null", CircleLayer.defaultCircleTranslate)
    assertNotNull("defaultCircleTranslateAsExpression should not be null", CircleLayer.defaultCircleTranslateAsExpression)
    assertNotNull("defaultCircleTranslateTransition should not be null", CircleLayer.defaultCircleTranslateTransition)
    assertNotNull("defaultCircleTranslateAnchor should not be null", CircleLayer.defaultCircleTranslateAnchor)
    assertNotNull("defaultCircleTranslateAnchorAsExpression should not be null", CircleLayer.defaultCircleTranslateAnchorAsExpression)
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
    val circleSortKeyTestValue = 1.0
    val circleBlurTestValue = 1.0
    val circleColorTestValue = "rgba(0, 0, 0, 1)"
    val circleEmissiveStrengthTestValue = 1.0
    val circleOpacityTestValue = 1.0
    val circlePitchAlignmentTestValue = CirclePitchAlignment.MAP
    val circlePitchScaleTestValue = CirclePitchScale.MAP
    val circleRadiusTestValue = 1.0
    val circleStrokeColorTestValue = "rgba(0, 0, 0, 1)"
    val circleStrokeOpacityTestValue = 1.0
    val circleStrokeWidthTestValue = 1.0
    val circleTranslateTestValue = listOf(0.0, 1.0)
    val circleTranslateAnchorTestValue = CircleTranslateAnchor.MAP

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = circleLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      circleSortKey(circleSortKeyTestValue)
      circleBlur(circleBlurTestValue)
      circleColor(circleColorTestValue)
      circleEmissiveStrength(circleEmissiveStrengthTestValue)
      circleOpacity(circleOpacityTestValue)
      circlePitchAlignment(circlePitchAlignmentTestValue)
      circlePitchScale(circlePitchScaleTestValue)
      circleRadius(circleRadiusTestValue)
      circleStrokeColor(circleStrokeColorTestValue)
      circleStrokeOpacity(circleStrokeOpacityTestValue)
      circleStrokeWidth(circleStrokeWidthTestValue)
      circleTranslate(circleTranslateTestValue)
      circleTranslateAnchor(circleTranslateAnchorTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as CircleLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(circleSortKeyTestValue, cachedLayer.circleSortKey)
    assertEquals(circleBlurTestValue, cachedLayer.circleBlur)
    assertEquals(circleColorTestValue, cachedLayer.circleColor)
    assertEquals(circleEmissiveStrengthTestValue, cachedLayer.circleEmissiveStrength)
    assertEquals(circleOpacityTestValue, cachedLayer.circleOpacity)
    assertEquals(circlePitchAlignmentTestValue, cachedLayer.circlePitchAlignment)
    assertEquals(circlePitchScaleTestValue, cachedLayer.circlePitchScale)
    assertEquals(circleRadiusTestValue, cachedLayer.circleRadius)
    assertEquals(circleStrokeColorTestValue, cachedLayer.circleStrokeColor)
    assertEquals(circleStrokeOpacityTestValue, cachedLayer.circleStrokeOpacity)
    assertEquals(circleStrokeWidthTestValue, cachedLayer.circleStrokeWidth)
    assertEquals(circleTranslateTestValue, cachedLayer.circleTranslate)
    assertEquals(circleTranslateAnchorTestValue, cachedLayer.circleTranslateAnchor)
  }
}

// End of generated file.
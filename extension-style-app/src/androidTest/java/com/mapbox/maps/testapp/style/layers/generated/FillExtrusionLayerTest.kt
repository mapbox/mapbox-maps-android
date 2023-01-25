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
 * Basic smoke tests for FillExtrusionLayer
 */
@RunWith(AndroidJUnit4::class)
class FillExtrusionLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun sourceLayerTest() {
    val layer = fillExtrusionLayer("id", "source") {
      sourceLayer("test")
    }
    setupLayer(layer)
    assertEquals("test", layer.sourceLayer)
  }

  @Test
  @UiThreadTest
  fun minZoomTest() {
    val layer = fillExtrusionLayer("id", "source") {
      minZoom(10.0)
    }
    setupLayer(layer)
    assertEquals(10.0, layer.minZoom)
  }

  @Test
  @UiThreadTest
  fun maxZoomTest() {
    val layer = fillExtrusionLayer("id", "source") {
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
    val layer = fillExtrusionLayer("id", "source") {
      filter(expression)
    }
    setupLayer(layer)
    assertEquals(expression.toString(), layer.filter.toString())
  }
  // Property getters and setters

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionIntensityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionIntensity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionIntensityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionIntensityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionIntensityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadius(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionAmbientOcclusionRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadius(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionRadiusAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionAmbientOcclusionRadius!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadiusTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionAmbientOcclusionRadiusTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionAmbientOcclusionRadiusTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionAmbientOcclusionRadiusTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBase(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionBase!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBase(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionBaseAsExpression.toString())
    assertEquals(null, layer.fillExtrusionBase)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBaseTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionBaseTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionBaseTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionBaseTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionBaseTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionColor?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorAsExpressionTest() {
    val expression = toColor {
      get {
        literal("color")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionColorAsExpression.toString())
    assertEquals(null, layer.fillExtrusionColor)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorAsColorIntTest() {
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.fillExtrusionColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionColorTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeight(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionHeight!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightAsExpressionTest() {
    val expression = number {
      get {
        literal("number")
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeight(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionHeightAsExpression.toString())
    assertEquals(null, layer.fillExtrusionHeight)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeightTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionHeightTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionHeightTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionHeightTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionHeightTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityTest() {
    val testValue = 1.0
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.fillExtrusionOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.fillExtrusionOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.fillExtrusionOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionPatternTest() {
    val testValue = "abc"
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionPattern(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionPattern?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionPatternAsExpressionTest() {
    val expression = image {
      string {
        get {
          literal("resolvedImage")
        }
      }
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionPattern(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionPatternAsExpression.toString())
    assertEquals(null, layer.fillExtrusionPattern)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateTest() {
    val testValue = listOf(0.0, 1.0)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslate(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionTranslate?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateAsExpressionTest() {
    val expression = literal(listOf(0.0, 1.0))
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslate(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionTranslateAsExpression.toString())
    assertEquals(listOf(0.0, 1.0), layer.fillExtrusionTranslate!!)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.fillExtrusionTranslateTransition)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateAnchorTest() {
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateAnchor(FillExtrusionTranslateAnchor.MAP)
    }
    setupLayer(layer)
    assertEquals(FillExtrusionTranslateAnchor.MAP, layer.fillExtrusionTranslateAnchor)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionTranslateAnchorAsExpressionTest() {
    val expression = literal("map")
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionTranslateAnchor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionTranslateAnchorAsExpression.toString())
    assertEquals(FillExtrusionTranslateAnchor.MAP, layer.fillExtrusionTranslateAnchor!!)
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalGradientTest() {
    val testValue = true
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalGradient(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.fillExtrusionVerticalGradient?.toString())
  }

  @Test
  @UiThreadTest
  fun fillExtrusionVerticalGradientAsExpressionTest() {
    val expression = literal(true)
    val layer = fillExtrusionLayer("id", "source") {
      fillExtrusionVerticalGradient(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.fillExtrusionVerticalGradientAsExpression.toString())
    assertEquals(true, layer.fillExtrusionVerticalGradient!!)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = fillExtrusionLayer("id", "source") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", FillExtrusionLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", FillExtrusionLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", FillExtrusionLayer.defaultMaxZoom)
    assertNotNull("defaultFillExtrusionAmbientOcclusionIntensity should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensity)
    assertNotNull("defaultFillExtrusionAmbientOcclusionIntensityAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionIntensityTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionIntensityTransition)
    assertNotNull("defaultFillExtrusionAmbientOcclusionRadius should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadius)
    assertNotNull("defaultFillExtrusionAmbientOcclusionRadiusAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusAsExpression)
    assertNotNull("defaultFillExtrusionAmbientOcclusionRadiusTransition should not be null", FillExtrusionLayer.defaultFillExtrusionAmbientOcclusionRadiusTransition)
    assertNotNull("defaultFillExtrusionBase should not be null", FillExtrusionLayer.defaultFillExtrusionBase)
    assertNotNull("defaultFillExtrusionBaseAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionBaseAsExpression)
    assertNotNull("defaultFillExtrusionBaseTransition should not be null", FillExtrusionLayer.defaultFillExtrusionBaseTransition)
    assertNotNull("defaultFillExtrusionColor should not be null", FillExtrusionLayer.defaultFillExtrusionColor)
    assertNotNull("defaultFillExtrusionColorAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionColorAsExpression)
    assertNotNull("defaultFillExtrusionColorAsColorInt should not be null", FillExtrusionLayer.defaultFillExtrusionColorAsColorInt)
    assertNotNull("defaultFillExtrusionColorTransition should not be null", FillExtrusionLayer.defaultFillExtrusionColorTransition)
    assertNotNull("defaultFillExtrusionHeight should not be null", FillExtrusionLayer.defaultFillExtrusionHeight)
    assertNotNull("defaultFillExtrusionHeightAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionHeightAsExpression)
    assertNotNull("defaultFillExtrusionHeightTransition should not be null", FillExtrusionLayer.defaultFillExtrusionHeightTransition)
    assertNotNull("defaultFillExtrusionOpacity should not be null", FillExtrusionLayer.defaultFillExtrusionOpacity)
    assertNotNull("defaultFillExtrusionOpacityAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionOpacityAsExpression)
    assertNotNull("defaultFillExtrusionOpacityTransition should not be null", FillExtrusionLayer.defaultFillExtrusionOpacityTransition)
    assertNotNull("defaultFillExtrusionPattern should not be null", FillExtrusionLayer.defaultFillExtrusionPattern)
    assertNotNull("defaultFillExtrusionPatternAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionPatternAsExpression)
    assertNotNull("defaultFillExtrusionTranslate should not be null", FillExtrusionLayer.defaultFillExtrusionTranslate)
    assertNotNull("defaultFillExtrusionTranslateAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateAsExpression)
    assertNotNull("defaultFillExtrusionTranslateTransition should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateTransition)
    assertNotNull("defaultFillExtrusionTranslateAnchor should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateAnchor)
    assertNotNull("defaultFillExtrusionTranslateAnchorAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionTranslateAnchorAsExpression)
    assertNotNull("defaultFillExtrusionVerticalGradient should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalGradient)
    assertNotNull("defaultFillExtrusionVerticalGradientAsExpression should not be null", FillExtrusionLayer.defaultFillExtrusionVerticalGradientAsExpression)
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
    val fillExtrusionAmbientOcclusionIntensityTestValue = 1.0
    val fillExtrusionAmbientOcclusionRadiusTestValue = 1.0
    val fillExtrusionBaseTestValue = 1.0
    val fillExtrusionColorTestValue = "rgba(0, 0, 0, 1)"
    val fillExtrusionHeightTestValue = 1.0
    val fillExtrusionOpacityTestValue = 1.0
    val fillExtrusionPatternTestValue = "abc"
    val fillExtrusionTranslateTestValue = listOf(0.0, 1.0)
    val fillExtrusionTranslateAnchorTestValue = FillExtrusionTranslateAnchor.MAP
    val fillExtrusionVerticalGradientTestValue = true

    val minZoomTestValue = 10.0
    val maxZoomTestValue = 20.0
    val layer = fillExtrusionLayer("id", "source") {
      sourceLayer("test")
      minZoom(minZoomTestValue)
      maxZoom(maxZoomTestValue)
      filter(filterTestValue)
      fillExtrusionAmbientOcclusionIntensity(fillExtrusionAmbientOcclusionIntensityTestValue)
      fillExtrusionAmbientOcclusionRadius(fillExtrusionAmbientOcclusionRadiusTestValue)
      fillExtrusionBase(fillExtrusionBaseTestValue)
      fillExtrusionColor(fillExtrusionColorTestValue)
      fillExtrusionHeight(fillExtrusionHeightTestValue)
      fillExtrusionOpacity(fillExtrusionOpacityTestValue)
      fillExtrusionPattern(fillExtrusionPatternTestValue)
      fillExtrusionTranslate(fillExtrusionTranslateTestValue)
      fillExtrusionTranslateAnchor(fillExtrusionTranslateAnchorTestValue)
      fillExtrusionVerticalGradient(fillExtrusionVerticalGradientTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as FillExtrusionLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals("test", cachedLayer.sourceLayer)
    assertEquals(minZoomTestValue, cachedLayer.minZoom)
    assertEquals(maxZoomTestValue, cachedLayer.maxZoom)
    assertEquals(filterTestValue.toString(), cachedLayer.filter.toString())
    assertEquals(fillExtrusionAmbientOcclusionIntensityTestValue, cachedLayer.fillExtrusionAmbientOcclusionIntensity)
    assertEquals(fillExtrusionAmbientOcclusionRadiusTestValue, cachedLayer.fillExtrusionAmbientOcclusionRadius)
    assertEquals(fillExtrusionBaseTestValue, cachedLayer.fillExtrusionBase)
    assertEquals(fillExtrusionColorTestValue, cachedLayer.fillExtrusionColor)
    assertEquals(fillExtrusionHeightTestValue, cachedLayer.fillExtrusionHeight)
    assertEquals(fillExtrusionOpacityTestValue, cachedLayer.fillExtrusionOpacity)
    assertEquals(fillExtrusionPatternTestValue, cachedLayer.fillExtrusionPattern)
    assertEquals(fillExtrusionTranslateTestValue, cachedLayer.fillExtrusionTranslate)
    assertEquals(fillExtrusionTranslateAnchorTestValue, cachedLayer.fillExtrusionTranslateAnchor)
    assertEquals(fillExtrusionVerticalGradientTestValue, cachedLayer.fillExtrusionVerticalGradient)
  }
}

// End of generated file.
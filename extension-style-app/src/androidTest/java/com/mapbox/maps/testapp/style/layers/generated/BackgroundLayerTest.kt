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
 * Basic smoke tests for BackgroundLayer
 */
@RunWith(AndroidJUnit4::class)
class BackgroundLayerTest : BaseStyleTest() {
  // Property getters and setters

  @Test
  @UiThreadTest
  fun backgroundColorTest() {
    val testValue = "rgba(0, 0, 0, 1)"
    val layer = backgroundLayer("id") {
      backgroundColor(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.backgroundColor?.toString())
  }

  @Test
  @UiThreadTest
  fun backgroundColorAsExpressionTest() {
    val expression = rgba {
      literal(0.0)
      literal(0.0)
      literal(0.0)
      literal(1.0)
    }
    val layer = backgroundLayer("id") {
      backgroundColor(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.backgroundColorAsExpression.toString())
    assertEquals("rgba(0, 0, 0, 1)", layer.backgroundColor)
    assertEquals(Color.BLACK, layer.backgroundColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun backgroundColorAsColorIntTest() {
    val layer = backgroundLayer("id") {
      backgroundColor(Color.CYAN)
    }
    setupLayer(layer)
    assertEquals(Color.CYAN, layer.backgroundColorAsColorInt)
  }

  @Test
  @UiThreadTest
  fun backgroundColorTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = backgroundLayer("id") {
      backgroundColorTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.backgroundColorTransition)
  }

  @Test
  @UiThreadTest
  fun backgroundColorTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = backgroundLayer("id") {
      backgroundColorTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.backgroundColorTransition)
  }

  @Test
  @UiThreadTest
  fun backgroundOpacityTest() {
    val testValue = 1.0
    val layer = backgroundLayer("id") {
      backgroundOpacity(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue, layer.backgroundOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun backgroundOpacityAsExpressionTest() {
    val expression = literal(1.0)
    val layer = backgroundLayer("id") {
      backgroundOpacity(expression)
    }
    setupLayer(layer)

    assertEquals(1.0, layer.backgroundOpacityAsExpression?.contents as Double, 1E-5)
    assertEquals(1.0, layer.backgroundOpacity!!, 1E-5)
  }

  @Test
  @UiThreadTest
  fun backgroundOpacityTransitionTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = backgroundLayer("id") {
      backgroundOpacityTransition(transition)
    }
    setupLayer(layer)
    assertEquals(transition, layer.backgroundOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun backgroundOpacityTransitionSetDslTest() {
    val transition = transitionOptions {
      duration(100)
      delay(200)
    }
    val layer = backgroundLayer("id") {
      backgroundOpacityTransition {
        duration(100)
        delay(200)
      }
    }
    setupLayer(layer)
    assertEquals(transition, layer.backgroundOpacityTransition)
  }

  @Test
  @UiThreadTest
  fun backgroundPatternTest() {
    val testValue = "abc"
    val layer = backgroundLayer("id") {
      backgroundPattern(testValue)
    }
    setupLayer(layer)
    assertEquals(testValue.toString(), layer.backgroundPattern?.toString())
  }

  @Test
  @UiThreadTest
  fun backgroundPatternAsExpressionTest() {
    val expression = literal("abc")
    val layer = backgroundLayer("id") {
      backgroundPattern(expression)
    }
    setupLayer(layer)

    assertEquals(expression.toString(), layer.backgroundPatternAsExpression.toString())
    assertEquals(expression.toString(), layer.backgroundPattern)
  }

  @Test
  @UiThreadTest
  fun visibilityTest() {
    val layer = backgroundLayer("id") {
      visibility(Visibility.NONE)
    }
    setupLayer(layer)
    assertEquals(Visibility.NONE, layer.visibility)
  }

  // Default property getter tests

  @Test
  @UiThreadTest
  fun defaultLayerPropertiesTest() {
    assertNotNull("defaultVisibility should not be null", BackgroundLayer.defaultVisibility)
    assertNotNull("defaultMinZoom should not be null", BackgroundLayer.defaultMinZoom)
    assertNotNull("defaultMaxZoom should not be null", BackgroundLayer.defaultMaxZoom)
    assertNotNull("defaultBackgroundColor should not be null", BackgroundLayer.defaultBackgroundColor)
    assertNotNull("defaultBackgroundColorAsExpression should not be null", BackgroundLayer.defaultBackgroundColorAsExpression)
    assertNotNull("defaultBackgroundColorAsColorInt should not be null", BackgroundLayer.defaultBackgroundColorAsColorInt)
    assertNotNull("defaultBackgroundColorTransition should not be null", BackgroundLayer.defaultBackgroundColorTransition)
    assertNotNull("defaultBackgroundOpacity should not be null", BackgroundLayer.defaultBackgroundOpacity)
    assertNotNull("defaultBackgroundOpacityAsExpression should not be null", BackgroundLayer.defaultBackgroundOpacityAsExpression)
    assertNotNull("defaultBackgroundOpacityTransition should not be null", BackgroundLayer.defaultBackgroundOpacityTransition)
    assertNotNull("defaultBackgroundPattern should not be null", BackgroundLayer.defaultBackgroundPattern)
    assertNotNull("defaultBackgroundPatternAsExpression should not be null", BackgroundLayer.defaultBackgroundPatternAsExpression)
  }

  @Test
  @UiThreadTest
  fun getLayerTest() {
    val backgroundColorTestValue = "rgba(0, 0, 0, 1)"
    val backgroundOpacityTestValue = 1.0
    val backgroundPatternTestValue = "abc"

    val layer = backgroundLayer("id") {
      backgroundColor(backgroundColorTestValue)
      backgroundOpacity(backgroundOpacityTestValue)
      backgroundPattern(backgroundPatternTestValue)
    }

    setupLayer(layer)

    val cachedLayer = getLayer("id") as BackgroundLayer

    removeLayer(layer)
    setupLayer(cachedLayer)

    assertEquals(backgroundColorTestValue, cachedLayer.backgroundColor)
    assertEquals(backgroundOpacityTestValue, cachedLayer.backgroundOpacity)
    assertEquals(backgroundPatternTestValue, cachedLayer.backgroundPattern)
  }
}

// End of generated file.